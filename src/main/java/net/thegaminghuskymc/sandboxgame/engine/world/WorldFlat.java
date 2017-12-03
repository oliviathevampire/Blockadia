package net.thegaminghuskymc.sandboxgame.engine.world;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;

import java.util.ArrayList;

public abstract class WorldFlat extends World {

	/** world weather */
	private Sky sky;

	public WorldFlat() {
		super();
		this.sky = new Sky();
	}

	protected final WorldTerrainStorage instanciateTerrainStorage() {
		return (new WorldFlatTerrainStorage(this));
	}

	/** tasks to be run to update the world */
	@Override
	protected void onTasksGet(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {
		this.sky.getTasks(engine, tasks);
	}

	/** return world weather */
	public Sky getSky() {
		return (this.sky);
	}

	@Override
	public String toString() {
		return ("WorldFlat: " + this.getName());
	}

	/** return world name */
	public abstract String getName();
}
