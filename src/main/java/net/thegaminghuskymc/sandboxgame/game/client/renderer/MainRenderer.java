package net.thegaminghuskymc.sandboxgame.game.client.renderer;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLVertexArray;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLVertexBuffer;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.event.GLFWEventWindowResize;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.event.GLFWListener;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.lines.LineRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.particles.ParticleRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.sky.SkyRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.resources.ResourceManagerClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.util.ArrayList;
import java.util.Random;

public class MainRenderer implements Taskable {

	/**
	 * screen referentials (bottom left to top right) varies from
	 * 
	 * (0, 0) to (1,1) from window referential
	 * 
	 * -1, -1) to (1, 1) from openg referential
	 */
	public static final Matrix4f WINDOW_TO_GL_BASIS = new Matrix4f();

	static {
		WINDOW_TO_GL_BASIS.translate(-1.0f, -1.0f, 0.0f);
		WINDOW_TO_GL_BASIS.scale(2.0f, 2.0f, 1.0f);
	}

	public static final Matrix4f GL_TO_WINDOW_BASIS = new Matrix4f();

	static {
		Matrix4f.invert(WINDOW_TO_GL_BASIS, GL_TO_WINDOW_BASIS);
	}

	/** resource manager */
	private GameEngineClient engine;

	/** custom renderers */
	private ArrayList<Renderer> customRenderers;
	private ArrayList<Renderer> defaultRenderers;

	/** GuiRenderer */
	private GuiRenderer guiRenderer;

	/** sky renderer */
	private SkyRenderer skyRenderer;

	/** line renderer */
	private LineRenderer lineRenderer;

	/** main terrain renderer */
	private TerrainRenderer terrainRenderer;

	/** model renderer */
	private ModelRenderer modelRenderer;

	/** particles renderer */
	private ParticleRenderer particleRenderer;

	/** random number generator */
	private Random rng;

	/** event instances (so we do not realloc them every frames */
	private EventPreRender preRenderEvent;
	private EventPostRender postRenderEvent;

	/** values */
	private int verticesDrawn;
	private int drawCalls;

	/** default and simple vao (to use geometry shaders) */
	private GLVertexArray defaultVao;
	private GLVertexBuffer defaultVbo;

	private boolean toggle = true;

	private GLFWListener<GLFWEventWindowResize> windowResizeListener;

	public MainRenderer(GameEngineClient engine) {
		this.engine = engine;
	}

	/** called after resources where loaded */
	public void initialize() {

		GLH.glhCheckError("Pre mainrenderer initialization");
		Logger.get().log(Logger.Level.FINE, "Initializing " + this.getClass().getSimpleName());

		this.customRenderers = new ArrayList<Renderer>();
		this.defaultRenderers = new ArrayList<Renderer>();

		this.rng = new Random();
		this.preRenderEvent = new EventPreRender(this);
		this.postRenderEvent = new EventPostRender(this);

		this.initialiseDefaultVAO();

		this.terrainRenderer = new TerrainRenderer(this);
		this.defaultRenderers.add(this.terrainRenderer);

		this.skyRenderer = new SkyRenderer(this);
		this.defaultRenderers.add(this.skyRenderer);

		this.particleRenderer = new ParticleRenderer(this);
		this.defaultRenderers.add(this.particleRenderer);

		this.lineRenderer = new LineRenderer(this);
		this.defaultRenderers.add(this.lineRenderer);

		this.modelRenderer = new ModelRenderer(this);
		this.defaultRenderers.add(this.modelRenderer);

		this.guiRenderer = new GuiRenderer(this);
		this.defaultRenderers.add(this.guiRenderer);

		GLH.glhCheckError("pre renderer initialization");
		for (Renderer renderer : this.defaultRenderers) {
			renderer.initialize();
			GLH.glhCheckError("post " + renderer.getClass().getSimpleName() + " initializes");
		}

		this.windowResizeListener = new GLFWListener<GLFWEventWindowResize>() {
			@Override
			public void invoke(GLFWEventWindowResize event) {
				onWindowResize(event.getGLFWWindow());
			}
		};
		this.getGLFWWindow().addListener(this.windowResizeListener);
		this.onWindowResize(this.getGLFWWindow());

		Logger.get().log(Logger.Level.FINE, "Done");
		GLH.glhCheckError("post mainrenderer started");
	}

	public void deinitialize() {
		this.getGLFWWindow().removeListener(this.windowResizeListener);

		GLH.glhCheckError("pre renderer deinitialization");
		for (Renderer renderer : this.defaultRenderers) {
			renderer.deinitialize();
			GLH.glhCheckError("post " + renderer.getClass().getSimpleName() + " deinitializes");
		}

		for (Renderer renderer : this.customRenderers) {
			renderer.deinitialize();
		}

		GLH.glhDeleteObject(this.defaultVao);
		this.defaultVao = null;

		GLH.glhDeleteObject(this.defaultVbo);
		this.defaultVbo = null;
	}

	private void initialiseDefaultVAO() {
		GLH.glhCheckError("pre default vao");

		this.defaultVao = GLH.glhGenVAO();
		this.defaultVbo = GLH.glhGenVBO();

		this.defaultVao.bind();
		this.defaultVbo.bind(GL15.GL_ARRAY_BUFFER);
		this.defaultVao.enableAttribute(0);
		this.defaultVao.setAttribute(0, 1, GL11.GL_FLOAT, false, 0, 0);

		float[] points = { 0 };
		this.defaultVbo.bufferData(GL15.GL_ARRAY_BUFFER, points, GL15.GL_STATIC_DRAW);

		GLH.glhCheckError("post default vao");
	}

	/** called whenever the window is resized */
	public void onWindowResize(GLFWWindow window) {
		for (Renderer renderer : this.defaultRenderers) {
			renderer.onWindowResize(window);
		}

		for (Renderer renderer : this.customRenderers) {
			renderer.onWindowResize(window);
		}
	}

	/** return the default particle renderer */
	public final ParticleRenderer getParticleRenderer() {
		return (this.particleRenderer);
	}

	/** terrain renderer */
	public final TerrainRenderer getTerrainRenderer() {
		return (this.terrainRenderer);
	}

	/** line renderer */
	public final LineRenderer getLineRenderer() {
		return (this.lineRenderer);
	}

	/** font renderer */
	public final GuiRenderer getGuiRenderer() {
		return (this.guiRenderer);
	}

	/** model renderer */
	public final ModelRenderer getModelRenderer() {
		return (this.modelRenderer);
	}

	/** sky renderer */
	public final SkyRenderer getSkyRenderer() {
		return (this.skyRenderer);
	}

	/**
	 * main rendering function (screen is already cleared, and frame buffer will be
	 * swapped after this render
	 */
	public void render() {

		// if renderer is not enabled, return
		if (!this.toggle) {
			return;
		}

		// TODO move this somewhere else, if openal is thread safe
		// if (this.getCamera() != null) {
		// this.getCamera().update();
		// this.engine.getResourceManager().getSoundManager().update(this.getCamera());
		// }

		this.getResourceManager().getEventManager().invokeEvent(this.preRenderEvent);

		// reset these values before next rendering
		this.drawCalls = GLH.glhGetContext().resetDrawCalls();
		this.verticesDrawn = GLH.glhGetContext().resetDrawVertices();

		this.getDefaultVAO().bind();

		// render
		GLH.glhCheckError("pre main renderer render");
		for (Renderer renderer : this.customRenderers) {
			renderer.render();
			GLH.glhCheckError("post " + renderer.getClass().getSimpleName() + ".render()");
		}

		GL11.glViewport(0, 0, this.getGLFWWindow().getWidth(), this.getGLFWWindow().getHeight());
		this.guiRenderer.render();

		GLH.glhCheckError("post gui renderer render");

		this.getResourceManager().getEventManager().invokeEvent(this.postRenderEvent);
	}

	/**
	 * return the default vertex array. It contains a single attribute (bound on 0)
	 * with a VBO which contains a single float (this float is 0.0f) you should use
	 * it as a default VAO for geometry
	 */
	public GLVertexArray getDefaultVAO() {
		return (this.defaultVao);
	}

	/** return the draw calls for the last frame */
	public int getDrawCalls() {
		return (this.drawCalls);
	}

	/** return the vertices drawn for the last frame */
	public int getVerticesDrawn() {
		return (this.verticesDrawn);
	}

	/** get the resource manager */
	public ResourceManagerClient getResourceManager() {
		return (this.engine.getResourceManager());
	}

	/** OpenGL tasks to be realized in main thread */
	public interface GLTask {
		public void run();
	}

	/** get current window on which the main renderer is rendering */
	public GLFWWindow getGLFWWindow() {
		return (this.engine.getGLFWWindow());
	}

	public GameEngineClient getEngine() {
		return (this.engine);
	}

	public Random getRNG() {
		return (this.rng);
	}

	@Override
	public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {

		for (Renderer renderer : this.defaultRenderers) {
			renderer.getTasks(engine, tasks);
		}

		for (Renderer renderer : this.customRenderers) {
			renderer.getTasks(engine, tasks);
		}
	}

	/** add a renderer to be use on the main rendering loop */
	public final void addRenderer(Renderer renderer) {
		this.customRenderers.add(renderer);
	}

	public final void removeRenderer(Renderer renderer) {
		this.customRenderers.remove(renderer);
	}

	/** set to true of false weather you want to render or not */
	public void toggle(boolean b) {
		this.toggle = b;
	}

}
