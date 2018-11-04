package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.util.Identifier;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.Display;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.entity.Entity;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.world.tile.TileEntry;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.gui.Gui;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.light.Light;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.post.PostProcessing;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.shader.*;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.object.ICamera;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.object.Quad;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.util.Fbo;

import java.util.*;

public class MasterRenderer {

	public static float scale = 3f;
	private static Matrix4f projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);

	private TileShader tileShader;
	private TileRenderer tileRenderer;

	private EntityShader entityShader;
	private EntityRenderer entityRenderer;

	private QuadShader quadShader;
	private QuadRenderer quadRenderer;

	private LightShader lightShader;
	private LightRenderer lightRenderer;

	private GuiShader guiShader;
	private GuiRenderer guiRenderer;

	private Fbo fbo;
	private Fbo lightFbo;

	private Map<Identifier, List<TileEntry>> tiles;
	private Map<Identifier, List<Entity>> entities;
	private List<Quad> quads;
	private List<Light> lights;
	private List<Gui> guis;

	private static final Quad LIGHT = new Quad(new Vector3f(0, 0, -2), new Vector3f(), new Vector3f(Display.getWidth(), Display.getHeight(), 1), new Vector4f(0.2f, 0.2f, 0.2f, 1));

	public MasterRenderer() {
		this.tileShader = new TileShader();
		this.tileRenderer = new TileRenderer(tileShader);
		this.entityShader = new EntityShader();
		this.entityRenderer = new EntityRenderer(entityShader);
		this.quadShader = new QuadShader();
		this.quadRenderer = new QuadRenderer(quadShader);
		this.lightShader = new LightShader();
		this.lightRenderer = new LightRenderer(lightShader);
		this.guiShader = new GuiShader();
		this.guiRenderer = new GuiRenderer(guiShader);
		this.fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER, 2);
		this.lightFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.NONE);
		this.tiles = new HashMap<Identifier, List<TileEntry>>();
		this.entities = new HashMap<Identifier, List<Entity>>();
		this.quads = new ArrayList<Quad>();
		this.lights = new ArrayList<Light>();
		this.guis = new ArrayList<Gui>();
	}

	public void render(ICamera camera, double mouseX, double mouseY, float partialTicks) {
		for (Gui gui : guis) {
			gui.render(this, mouseX, mouseY, partialTicks);
		}

		this.fbo.bindFrameBuffer();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		this.tileRenderer.render(this.tiles, camera, partialTicks);
		this.entityRenderer.render(entities, camera, partialTicks);
		this.fbo.unbindFrameBuffer();

		this.lightFbo.bindFrameBuffer();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		this.quadRenderer.render(Arrays.asList(new Quad[] { LIGHT }), partialTicks);
		this.lightRenderer.render(this.lights, camera, partialTicks);
		this.lightFbo.unbindFrameBuffer();

		PostProcessing.doPostProcessing(fbo.getColorTexture(0), fbo.getColorTexture(1), lightFbo.getColorTexture());

		this.guiRenderer.render(this.guis, mouseX, mouseY, partialTicks);
		this.quadRenderer.render(this.quads, partialTicks);

		this.tiles.clear();
		this.entities.clear();
		this.quads.clear();
		this.lights.clear();
		this.guis.clear();
	}

	public void cleanUp() {
		this.tileRenderer.cleanUp();
		this.entityShader.cleanUp();
		this.quadRenderer.cleanUp();
		this.lightRenderer.cleanUp();
		this.fbo.cleanUp();
		this.lightFbo.cleanUp();
	}

	public void renderTile(TileEntry tile) {
		Identifier texture = tile.getTile().getTexture();
		List<TileEntry> batch = this.tiles.get(texture);
		if (batch == null) {
			batch = new ArrayList<TileEntry>();
			this.tiles.put(texture, batch);
		}
		batch.add(tile);
	}

	public void renderEntity(Entity entity) {
		Identifier texture = entity.getTexture();
		List<Entity> batch = this.entities.get(texture);
		if (batch == null) {
			batch = new ArrayList<Entity>();
			this.entities.put(texture, batch);
		}
		batch.add(entity);
	}

	public void renderQuads(Quad... quads) {
		for (int i = 0; i < quads.length; i++) {
			this.quads.add(quads[i]);
		}
	}

	public void renderLights(Light... lights) {
		for (int i = 0; i < lights.length; i++) {
			this.lights.add(lights[i]);
		}
	}

	public void renderGuis(Gui... guis) {
		for (int i = 0; i < guis.length; i++) {
			this.guis.add(guis[i]);
		}
	}

	public void setAmbientLight(float red, float green, float blue) {
		LIGHT.getColor().set(red, green, blue, 1);
	}

	public void setScale(float scale) {
		MasterRenderer.scale = scale;
		MasterRenderer.projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);
		this.tileRenderer.updateProjectionMatrix(projectionMatrix);
		this.entityRenderer.updateProjectionMatrix(projectionMatrix);
		this.lightRenderer.updateProjectionMatrix(projectionMatrix);
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}