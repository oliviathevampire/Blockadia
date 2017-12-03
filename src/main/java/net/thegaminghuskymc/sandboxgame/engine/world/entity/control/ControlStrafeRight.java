package net.thegaminghuskymc.sandboxgame.engine.world.entity.control;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.PhysicObject;

public class ControlStrafeRight extends Control<Entity> {
	@Override
	public void run(Entity entity, double dt) {
		//save velocity
		float vx = entity.getPositionVelocityX();
		float vy = entity.getPositionVelocityY();
		float vz = entity.getPositionVelocityZ();
		
		//set control velocity, and move
		Vector3f view = entity.getViewVector();
		entity.setPositionVelocity(-view.z * entity.getSpeed(), 0.0f, view.x * entity.getSpeed());
		PhysicObject.move(entity.getWorld(), entity, dt);
		
		//reset velocities
		entity.setPositionVelocity(vx, vy, vz);
	}
}
