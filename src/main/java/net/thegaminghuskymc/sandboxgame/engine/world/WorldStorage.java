package net.thegaminghuskymc.sandboxgame.engine.world;


import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;

public abstract class WorldStorage implements Taskable {

    /**
     * the world
     */
    private final World _world;

    public WorldStorage(World world) {
        this._world = world;
    }

    public World getWorld() {
        return (this._world);
    }

    public abstract void delete();

    protected void invokeEvent(Event event) {
        if (ResourceManager.instance() != null && ResourceManager.instance().getEventManager() != null) {
            ResourceManager.instance().getEventManager().invokeEvent(event);
        } else {
            Logger.get().log(Logger.Level.WARNING, "Tried to invoke an event before EventManager initialization");
        }
    }

}
