package net.thegaminghuskymc.sandboxgame.engine.events.world;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.engine.world.World;

/**
 * represents a world event
 */
public abstract class EventWorld extends Event {
    private World _world;

    public EventWorld(World world) {
        this._world = world;
    }

    public World getWorld() {
        return (this._world);
    }
}
