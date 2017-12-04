package net.thegaminghuskymc.sandboxgame.game.client.resources;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.events.EventListener;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntityDespawn;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntitySpawn;
import net.thegaminghuskymc.sandboxgame.engine.managers.EventManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.GenericManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.EventModelInstanceAdded;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.EventModelInstanceRemoved;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.Model;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/** handle models */
public class ModelManager extends GenericManager<Model> {

	/** the model hashmap */
	private HashMap<Class<? extends Entity>, Integer> entitiesModels;

	/** the model instances using the given model */
	private HashMap<Model, ArrayList<ModelInstance>> modelsModelInstances;

	/** the model instances of the entities */
	private HashMap<Entity, ModelInstance> entitiesModelInstance;

	/** the model manager */
	public ModelManager(ResourceManager resourceManager) {
		super(resourceManager);
	}

	@Override
	protected final void onInitialized() {
		this.entitiesModels = new HashMap<>();
		this.modelsModelInstances = new HashMap<>();
		this.entitiesModelInstance = new HashMap<>();
	}

	@Override
	protected final void onDeinitialized() {
		this.removeModelInstances();
		this.entitiesModels = null;
		this.modelsModelInstances = null;
		this.entitiesModelInstance = null;
	}

	@Override
	protected final void onUnloaded() {
		this.removeModelInstances();
	}

	@Override
	protected final void onLoaded() {

	}

	/** remove every model instances */
	public final void removeModelInstances() {
		Collection<ModelInstance> collection = this.entitiesModelInstance.values();
		ModelInstance[] modelInstances = collection.toArray(new ModelInstance[collection.size()]);
		for (ModelInstance modelInstance : modelInstances) {
			this.removeModelInstance(modelInstance);
		}
	}

	/** remove the given model instance */
	public final void removeModelInstance(ModelInstance modelInstance) {
		Model model = modelInstance.getModel();
		ArrayList<ModelInstance> modelInstances = this.modelsModelInstances.get(model);
		if (modelInstances == null) {
			Logger.get().log(Logger.Level.WARNING, "tried to despawn an un-spawned model instance: " + modelInstance);
			return;
		}
		modelInstances.remove(modelInstance);

		if (modelInstances.size() == 0) {
			this.modelsModelInstances.remove(modelInstance.getModel());
			model.deinitialize();
		}
		this.entitiesModelInstance.remove(modelInstance.getEntity());
		this.getResourceManager().getEventManager().invokeEvent(new EventModelInstanceRemoved(modelInstance));
	}

	/**
	 * WARNING: do not use it unless you know what you're doing. spawn a model
	 * instance
	 */
	public void addModelInstance(ModelInstance modelInstance) {
		Entity entity = modelInstance.getEntity();
		Model model = modelInstance.getModel();
		ArrayList<ModelInstance> modelInstances = this.modelsModelInstances.get(model);
		if (modelInstances == null) {
			modelInstances = new ArrayList<>(1);
			this.modelsModelInstances.put(model, modelInstances);
		}
		modelInstances.add(modelInstance);
		this.entitiesModelInstance.put(entity, modelInstance);
		this.getResourceManager().getEventManager().invokeEvent(new EventModelInstanceAdded(modelInstance));
	}

	/**
	 * register a new model, link it with the entity class, and return the model
	 * ID
	 */
	public int registerModel(Model model) {
		return (super.registerObject(model));
	}

	/**
	 * attach an entity to a model, so when the entity spawns, a new model
	 * instance is created
	 * 
	 * @param entityClass
	 *            : the entity class to attach
	 * @param modelID
	 *            : the registered modelID
	 */
	public void attachEntityToModel(Class<? extends Entity> entityClass, Integer modelID) {
		this.entitiesModels.put(entityClass, modelID);
	}

	/** get the model instance for the given entity */
	public final ModelInstance getModelInstance(Entity entity) {
		return (this.entitiesModelInstance.get(entity));
	}

	@Override
	protected void onObjectRegistered(Model model) {
		Logger.get().log(Logger.Level.FINE, "new model registered", model);
	}

}
