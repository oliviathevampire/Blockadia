package net.thegaminghuskymc.sandboxgame.engine.events.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;

public class EventEntityPlaySound extends EventEntity {

	private final String sound;

	public EventEntityPlaySound(Entity entity, String sound) {
		super(entity);
		this.sound = sound;
	}

	public final String getSound() {
		return (this.sound);
	}
}
