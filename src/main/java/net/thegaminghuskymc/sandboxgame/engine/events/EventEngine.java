package net.thegaminghuskymc.sandboxgame.engine.events;


import net.thegaminghuskymc.sandboxgame.engine.GameEngine;

public abstract class EventEngine extends Event {
    private GameEngine engine;

    public EventEngine(GameEngine engine) {
        super();
        this.engine = engine;
    }

    public final GameEngine getEngine() {
        return (this.engine);
    }
}
