package net.thegaminghuskymc.sandboxgame.engine.events.world;


import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public class EventTerrain extends Event {

    private final Terrain terrain;

    public EventTerrain(Terrain terrain) {
        super();
        this.terrain = terrain;
    }

    public final Terrain getTerrain() {
        return (this.terrain);
    }
}
