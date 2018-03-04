package net.thegaminghuskymc.sandboxgame.engine.managers;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;

public class EntityManager extends GenericManager<Class<? extends WorldEntity>> {

    public EntityManager(ResourceManager resource_manager) {
        super(resource_manager);
    }

    /**
     * register a new entity from it class
     */
    public int registerEntity(Class<? extends WorldEntity> entityclass) {
        if (super.hasObject(entityclass)) {
            Logger.get().log(Logger.Level.WARNING,
                    "Tried to register an already registered entity: " + entityclass.getSimpleName());
            return (-1);
        }
        Logger.get().log(Logger.Level.FINE, "Registered an entity: " + entityclass.getSimpleName());
        return (super.registerObject(entityclass));
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
    protected void onObjectRegistered(Class<? extends WorldEntity> object) {
    }
}
