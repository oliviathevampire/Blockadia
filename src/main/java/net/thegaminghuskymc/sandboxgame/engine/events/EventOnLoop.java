package net.thegaminghuskymc.sandboxgame.engine.events;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;

/**
 * an event which is called during the main game loop
 */
public class EventOnLoop extends EventEngine {

    public EventOnLoop(GameEngine engine) {
        super(engine);
    }
}