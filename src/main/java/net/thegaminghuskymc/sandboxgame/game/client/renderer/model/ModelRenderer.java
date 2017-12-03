package net.thegaminghuskymc.sandboxgame.game.client.renderer.model;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.Renderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelRenderer extends Renderer {

	/** the rendering program */
	private ProgramModel programModel;

	public ModelRenderer(MainRenderer mainRenderer) {
		super(mainRenderer);
	}

	@Override
	public void initialize() {
		this.programModel = new ProgramModel();
	}

	@Override
	public void deinitialize() {

		GLH.glhDeleteObject(this.programModel);
		this.programModel = null;
	}

	/** render world terrains */
	public void render(CameraProjective camera, HashMap<Model, ArrayList<ModelInstance>> renderingList) {

		if (this.getMainRenderer().getGLFWWindow().isKeyPressed(GLFW.GLFW_KEY_F)) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		// enable model program
		this.programModel.useStart();
		{
			// load global uniforms
			this.programModel.loadCamera(camera);

			// for each entity to render
			for (ArrayList<ModelInstance> models : renderingList.values()) {
				if (models.size() > 0) {
					Model model = models.get(0).getModel();
					model.bind();
					this.programModel.loadModel(model);
					for (ModelInstance instance : models) {
						this.programModel.loadModelInstance(instance);
						model.draw();
					}
				}
			}
		}
		this.programModel.useStop();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

	@Override
	public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {
	}
}