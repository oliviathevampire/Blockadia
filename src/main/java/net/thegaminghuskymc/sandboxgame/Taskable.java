package net.thegaminghuskymc.sandboxgame;

import net.thegaminghuskymc.sandboxgame.GameEngine;

import java.util.ArrayList;

public interface Taskable {

    public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks);
}
