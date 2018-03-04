package net.thegaminghuskymc.sandboxgame.engine.events.world;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.engine.world.World;

/** represents a world event */
public abstract class EventWorld extends Event {
	private final World world;

	public EventWorld(World world) {
		super();
		this.world = world;
	}

	public final World getWorld() {
		return (this.world);
	}
}
