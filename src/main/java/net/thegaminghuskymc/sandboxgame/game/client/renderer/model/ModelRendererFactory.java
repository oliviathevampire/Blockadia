package net.thegaminghuskymc.sandboxgame.game.client.renderer.model;

import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.RendererFactory;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * a factory class which create model renderer lists
 */
public class ModelRendererFactory extends RendererFactory {

    /**
     * the entity in frustum list
     */
    private HashMap<Model, ArrayList<ModelInstance>> renderingList;
    private ArrayList<ModelInstance> modelInstances;

    /**
     * world and camera
     */
    private CameraProjective camera;

    public ModelRendererFactory(MainRenderer mainRenderer) {
        super(mainRenderer);
        this.renderingList = new HashMap<>();
        this.modelInstances = new ArrayList<>();
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

        for (ModelInstance modelInstance : this.modelInstances) {
            if (modelInstance == null || !modelInstance.getEntity().isVisible()) {
                continue;
            }
            modelInstance.update();

            ArrayList<ModelInstance> instances = this.renderingList.get(modelInstance.getModel());
            if (instances == null) {
                instances = new ArrayList<>(1);
                this.renderingList.put(modelInstance.getModel(), instances);
            }
            instances.add(modelInstance);
        }
    }

    /**
     * get the last calculated entities in frustum
     */
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
        for (Entity entity : world.getEntityStorage().getEntities()) {
            ModelInstance modelInstance = this.getResourceManager().getModelManager().getModelInstance(entity);
            if (modelInstance == null) {
                continue;
            }
            this.modelInstances.add(modelInstance);
        }
    }
}