package net.thegaminghuskymc.sandboxgame.engine.events;

public abstract class Event {

	public String getName() {
		return (this.getClass().getSimpleName());
	}
}
