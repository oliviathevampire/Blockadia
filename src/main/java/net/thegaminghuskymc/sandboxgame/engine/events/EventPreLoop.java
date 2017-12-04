package net.thegaminghuskymc.sandboxgame.engine.events;


import net.thegaminghuskymc.sandboxgame.engine.GameEngine;

/**
 * an event which is called right before the main loop ends
 */
public class EventPreLoop extends EventEngine {

    public EventPreLoop(GameEngine engine) {
        super(engine);
    }
}