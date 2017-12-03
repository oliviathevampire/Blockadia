package net.thegaminghuskymc.sandboxgame.engine.world.entity.control;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;

public class ControlRotateLeft extends Control<Entity> {
	@Override
	public void run(Entity entity, double dt) {
		entity.setRotationY(entity.getRotationY() - 2.0f);
	}
}
