package net.thegaminghuskymc.sandboxgame.engine.events;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;

/**
 * an event which is called right after the main loop ends
 */
public class EventPostLoop extends EventEngine {

    public EventPostLoop(GameEngine engine) {
        super(engine);
    }
}