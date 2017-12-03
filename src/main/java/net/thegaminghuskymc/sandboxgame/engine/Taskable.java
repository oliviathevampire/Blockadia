package net.thegaminghuskymc.sandboxgame.engine;

import java.util.ArrayList;

public interface Taskable {

	public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks);
}
