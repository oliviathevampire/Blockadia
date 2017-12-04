package net.thegaminghuskymc.sandboxgame.engine.managers;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.world.World;

import java.util.Collection;

/**
 * physic update thread pool
 */

public class WorldManager extends GenericManager<World> {

    public WorldManager(ResourceManager resource_manager) {
        super(resource_manager);
    }

    /**
     * register a world the to engine
     */
    public int registerWorld(World world) {
        if (super.hasObject(world)) {
            Logger.get().log(Logger.Level.WARNING, "Tried to register an already registered world: " + world);
            return (GenericManager.ERROR_OBJECT_ID);
        }
        Logger.get().log(Logger.Level.FINE, "Registered a world: " + world);
        return (super.registerObject(world));
    }

    public World getWorld(int worldID) {
        return (super.getObjectByID(worldID));
    }

    /**
     * return the list of worlds
     */
    public Collection<World> getWorlds() {
        return (super.getObjects());
    }

    @Override
    protected void onObjectRegistered(World object) {
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
}
