package net.thegaminghuskymc.sandboxgame.engine.events;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;

import java.util.ArrayList;


/**
 * an event which is called right after the main loop ends
 */
public class EventGetTasks extends EventEngine {

    private ArrayList<GameEngine.Callable<Taskable>> tasksList;

    public EventGetTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasksList) {
        super(engine);
        this.tasksList = tasksList;
    }

    public final ArrayList<GameEngine.Callable<Taskable>> getTasksList() {
        return (this.tasksList);
    }
}
