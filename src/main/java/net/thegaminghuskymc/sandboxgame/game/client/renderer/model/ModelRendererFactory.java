package net.thegaminghuskymc.sandboxgame.game.client.renderer.model;


import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.RendererFactory;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

import java.util.ArrayList;
import java.util.HashMap;

/** a factory class which create model renderer lists */
public class ModelRendererFactory extends RendererFactory {

	/** the entity in frustum list */
	private HashMap<Model, ArrayList<ModelInstance>> renderingList;
	private ArrayList<ModelInstance> modelInstances;

	/** world and camera */
	private CameraProjective camera;

	public ModelRendererFactory(MainRenderer mainRenderer) {
		super(mainRenderer);
		this.renderingList = new HashMap<Model, ArrayList<ModelInstance>>();
		this.modelInstances = new ArrayList<ModelInstance>();
	}

	public final CameraProjective getCamera() {
		return (this.camera);
	}

	public final void setCamera(CameraProjective camera) {
		this.camera = camera;
	}

	@Override
	public void update(double dt) {
		this.renderingList.clear();

		// for each entity of the world (maybe we can do better here?)
		for (ModelInstance modelInstance : this.modelInstances) {
			if (modelInstance == null || !modelInstance.getEntity().isVisible()) {
				continue;
			}
			modelInstance.update();

			// if entities is not too far and in frustum, then add it to the
			// list
			// if (Vector3f.distance(entity.getPosition(), camera.getPosition())
			// > camera.getRenderDistance()
			// || !camera.isBoxInFrustum(entity.getBoundingBox())) {
			// continue;
			// }

			ArrayList<ModelInstance> instances = this.renderingList.get(modelInstance.getModel());
			if (instances == null) {
				instances = new ArrayList<ModelInstance>(1);
				this.renderingList.put(modelInstance.getModel(), instances);
			}
			// VoxelEngineClient.instance().getRenderer().getWorldRenderer().getLineRenderer()
			// .addBox(modelInstance.getEntity().getBoundingBox());
			instances.add(modelInstance);
		}
	}

	/** get the last calculated entities in frustum */
	public HashMap<Model, ArrayList<ModelInstance>> getRenderingList() {
		return (this.renderingList);
	}

	@Override
	public void render() {
		this.getMainRenderer().getModelRenderer().render(this.getCamera(), this.getRenderingList());
	}

	public final void addModelInstance(ModelInstance modelInstance) {
		this.modelInstances.add(modelInstance);
	}

	public final void removeModelInstance(ModelInstance modelInstance) {
		this.modelInstances.remove(modelInstance);
	}

	public final void clear() {
		this.modelInstances.clear();
		this.renderingList.clear();
	}

	public final void loadWorldModelInstance(World world) {
		for (WorldEntity entity : world.getEntityStorage().getEntities()) {
			ModelInstance modelInstance = this.getResourceManager().getModelManager().getModelInstance(entity);
			if (modelInstance == null) {
				continue;
			}
			this.modelInstances.add(modelInstance);
		}
	}
}