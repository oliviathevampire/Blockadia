package net.thegaminghuskymc.sandboxgame.entity.forces;

import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.util.math.Vector3f;

public abstract class ForceFriction extends Force<Entity> {

    public ForceFriction() {
    }

    @Override
    public void onUpdateResultant(Entity entity, Vector3f resultant) {
        // F = 1/2 * p * v� * C * A

        float vx = entity.getPositionVelocityX();
        float vy = entity.getPositionVelocityY();
        float vz = entity.getPositionVelocityZ();

        float sx = entity.getSizeX();
        float sy = entity.getSizeY();
        float sz = entity.getSizeZ();

        float Ax = sy * sz;
        float Ay = sx * sz;
        float Az = sx * sy;

        float cp = 0.5f * this.getFluidDensity() * this.getDragCoefficient(entity);

        resultant.x -= vx * Ax * cp;
        resultant.y -= vy * Ay * cp;
        resultant.z -= vz * Az * cp;
    }

    /**
     * density of the fluid : kg.m^-3
     */
    public abstract float getFluidDensity();

    /**
     * fluid drag coefficient (constant) (no unit)
     */
    public abstract float getDragCoefficient(Entity entity);
}
