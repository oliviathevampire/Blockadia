package net.thegaminghuskymc.sandboxgame.game.client.resources;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.events.Listener;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntityDespawn;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntitySpawn;
import net.thegaminghuskymc.sandboxgame.engine.managers.EventManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.GenericManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.event.renderer.model.EventModelInstanceAdded;
import net.thegaminghuskymc.sandboxgame.game.client.event.renderer.model.EventModelInstanceRemoved;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.Model;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/** handle models */
public class ModelManager extends GenericManager<Model> {

	/** the model hashmap */
	private HashMap<Class<? extends WorldEntity>, Integer> entitiesModels;

	/** the model instances using the given model */
	private HashMap<Model, ArrayList<ModelInstance>> modelsModelInstances;

	/** the model instances of the entities */
	private HashMap<WorldEntity, ModelInstance> entitiesModelInstance;

	/** the model manager */
	public ModelManager(ResourceManager resourceManager) {
		super(resourceManager);
	}

	@Override
	protected final void onInitialized() {
		this.entitiesModels = new HashMap<Class<? extends WorldEntity>, Integer>();
		this.modelsModelInstances = new HashMap<Model, ArrayList<ModelInstance>>();
		this.entitiesModelInstance = new HashMap<WorldEntity, ModelInstance>();
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
		EventManager eventManager = this.getResourceManager().getEventManager();
		eventManager.addListener(new Listener<EventEntitySpawn>() {

			@Override
			public void pre(EventEntitySpawn event) {
			}

			@Override
			public void post(EventEntitySpawn event) {
				WorldEntity entity = event.getEntity();
				Model model = getModelForEntity(entity);
				if (model == null) {
					Logger.get().log(Logger.Level.ERROR, "No model for entity class", entity.getClass());
					return;
				}

				if (!model.isInitialized()) {
					Logger.get().log(Logger.Level.FINE, "Initializing model", model.getInitializer());
					model.initialize();
					Logger.get().log(Logger.Level.FINE, "Initialized model", model);
				}
				addModelInstance(new ModelInstance(model, entity));
			}
		});

		eventManager.addListener(new Listener<EventEntityDespawn>() {

			@Override
			public void pre(EventEntityDespawn event) {
			}

			@Override
			public void post(EventEntityDespawn event) {
				WorldEntity entity = event.getEntity();
				ModelInstance modelInstance = getModelInstance(entity);
				if (modelInstance == null) {
					return;
				}
				removeModelInstance(modelInstance);
			}
		});
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
	public final void addModelInstance(ModelInstance modelInstance) {
		WorldEntity entity = modelInstance.getEntity();
		Model model = modelInstance.getModel();
		ArrayList<ModelInstance> modelInstances = this.modelsModelInstances.get(model);
		if (modelInstances == null) {
			modelInstances = new ArrayList<ModelInstance>(1);
			this.modelsModelInstances.put(model, modelInstances);
		}
		modelInstances.add(modelInstance);
		this.entitiesModelInstance.put(entity, modelInstance);
		this.getResourceManager().getEventManager().invokeEvent(new EventModelInstanceAdded(modelInstance));
	}

	/**
	 * register a new model, link it with the entity class, and return the model ID
	 */
	public final int registerModel(Model model) {
		return (super.registerObject(model));
	}

	/**
	 * attach an entity to a model, so when the entity spawns, a new model instance
	 * is created
	 * 
	 * @param entityClass
	 *            : the entity class to attach
	 * @param modelID
	 *            : the registered modelID
	 */
	public final void attachEntityToModel(Class<? extends WorldEntity> entityClass, Integer modelID) {
		this.entitiesModels.put(entityClass, modelID);
	}

	/**
	 * get the model for the given entity
	 * 
	 * @param entityClass
	 *            : the entity class we want the model
	 * 
	 * @return : the model
	 */
	public final Model getModelForEntity(Class<? extends WorldEntity> entityClass) {
		if (!this.entitiesModels.containsKey(entityClass)) {
			return (null);
		}
		return (this.getModelByID(this.entitiesModels.get(entityClass)));
	}

	public final Model getModelForEntity(WorldEntity entity) {
		return (this.getModelForEntity(entity.getClass()));
	}

	/** get a model filepath by it id */
	public final Model getModelByID(int modelID) {
		return (super.getObjectByID(modelID));
	}

	/** get the model instance for the given entity */
	public final ModelInstance getModelInstance(WorldEntity entity) {
		return (this.entitiesModelInstance.get(entity));
	}

	/** get the list of the model instances using the given model */
	public final ArrayList<ModelInstance> getModelInstances(Model model) {
		return (this.modelsModelInstances.get(model));
	}

	@Override
	protected void onObjectRegistered(Model model) {
		Logger.get().log(Logger.Level.FINE, "new model registered", model);
	}

}
