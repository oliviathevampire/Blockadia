package net.thegaminghuskymc.sandboxgame.engine.events.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;

public class EventEntityPlaySound extends EventEntity {

	private final String sound;

	public EventEntityPlaySound(WorldEntity entity, String sound) {
		super(entity);
		this.sound = sound;
	}

	public final String getSound() {
		return (this.sound);
	}

	@Override
	protected void process() {
		// TODO
	}

	@Override
	protected void unprocess() {
		// TODO : stop sound!
	}

	@Override
	protected void onReset() {
		// TODO Auto-generated method stub

	}

}
