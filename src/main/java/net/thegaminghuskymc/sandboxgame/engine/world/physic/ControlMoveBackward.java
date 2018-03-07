package net.thegaminghuskymc.sandboxgame.engine.world.physic;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;

import javax.vecmath.Vector3f;

public class ControlMoveBackward extends Control<WorldEntity> {
    @Override
    public void run(WorldEntity entity, double dt) {
        // save velocity
        float vx = entity.getPositionVelocityX();
        float vy = entity.getPositionVelocityY();
        float vz = entity.getPositionVelocityZ();

        // set control velocity, and move
        Vector3f view = entity.getViewVector();
        entity.setPositionVelocity(-view.x * entity.getSpeed(), -view.y * entity.getSpeed(), 0.0f);
        WorldObject.move(entity.getWorld(), entity, dt);

        // reset velocities
        entity.setPositionVelocity(vx, vy, vz);
    }
}
