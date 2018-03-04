package net.thegaminghuskymc.sandboxgame.engine.events;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;

import java.util.ArrayList;

public class EventGetTasks extends Event {

	private ArrayList<GameEngine.Callable<Taskable>> tasksList;

	public EventGetTasks(ArrayList<GameEngine.Callable<Taskable>> tasksList) {
		super();
		this.tasksList = tasksList;
	}

	public final ArrayList<GameEngine.Callable<Taskable>> getTasksList() {
		return (this.tasksList);
	}

	@Override
	protected void process() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void unprocess() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onReset() {
		// TODO Auto-generated method stub

	}
}
