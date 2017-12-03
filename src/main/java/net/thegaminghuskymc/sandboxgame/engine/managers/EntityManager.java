package net.thegaminghuskymc.sandboxgame.engine.managers;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;

public class EntityManager extends GenericManager<Class<? extends Entity>> {

	public EntityManager(ResourceManager resource_manager) {
		super(resource_manager);
	}

	/** register a new entity from it class */
	public int registerEntity(Class<? extends Entity> entityclass) {
		if (super.hasObject(entityclass)) {
			Logger.get().log(Logger.Level.WARNING,
					"Tried to register an already registered entity: " + entityclass.getSimpleName());
			return (-1);
		}
		Logger.get().log(Logger.Level.FINE, "Registered an entity: " + entityclass.getSimpleName());
		return (super.registerObject(entityclass));
	}

	/** create a new instance of the given entity by it ID */
	@SuppressWarnings("unchecked")
	public <T> T newInstance(int entityID) {
		try {
			Class<? extends Entity> entityclass = super.getObjectByID(entityID);
			return (T) (entityclass.newInstance());
		} catch (Exception exception) {
			Logger.get().log(Logger.Level.ERROR, "Exception occured while creating new entity instance:");
			exception.printStackTrace(Logger.get().getPrintStream());
			return (null);
		}
	}

	@Override
	public void onInitialized() {
	}

	@Override
	public void onLoaded() {
	}

	@Override
	protected void onDeinitialized() {
	}

	@Override
	protected void onUnloaded() {
	}

	@Override
	protected void onObjectRegistered(Class<? extends Entity> object) {
	}
}
