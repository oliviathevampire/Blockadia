package net.thegaminghuskymc.sandboxgame.entity.collision;

public interface Rotationable {

    /**
     * @see Rotationable#rotate(Rotationable, float)
     */
    public static void rotate(Rotationable rotateable, double dt) {
        rotate(rotateable, (float) dt);
    }

    /**
     * rotate the rotateable objects depending on the elasped time 'dt' and set
     * acceleration and velocities
     *
     * @param rotateable : the rotateable object
     * @param dt         : elasped time
     */
    public static void rotate(Rotationable rotateable, float dt) {
        rotateable.setRotationVelocityX(rotateable.getRotationVelocityX() + rotateable.getRotationAccelerationX() * dt);
        rotateable.setRotationVelocityY(rotateable.getRotationVelocityY() + rotateable.getRotationAccelerationY() * dt);
        rotateable.setRotationVelocityZ(rotateable.getRotationVelocityZ() + rotateable.getRotationAccelerationZ() * dt);
        rotateable.setRotationX(rotateable.getRotationX() + rotateable.getRotationVelocityX() * dt);
        rotateable.setRotationY(rotateable.getRotationY() + rotateable.getRotationVelocityY() * dt);
        rotateable.setRotationZ(rotateable.getRotationZ() + rotateable.getRotationVelocityZ() * dt);
    }

    /**
     * @return true if this rotateable objects is rotating
     */
    public static boolean isRotating(Rotationable rotateable) {
        return (rotateable.getRotationVelocityX() != 0 || rotateable.getRotationVelocityY() != 0
                || rotateable.getRotationVelocityZ() != 0);
    }

    /**
     * rotation
     */
    public float getRotationX();

    /**
     * rotation
     */
    public void setRotationX(float x);

    public float getRotationY();

    public void setRotationY(float y);

    public float getRotationZ();

    public void setRotationZ(float z);

    /**
     * rotation velocity
     */
    public float getRotationVelocityX();

    /**
     * rotation velocity
     */
    public void setRotationVelocityX(float vx);

    public float getRotationVelocityY();

    public void setRotationVelocityY(float vy);

    public float getRotationVelocityZ();

    public void setRotationVelocityZ(float vz);

    /**
     * rotation acceleration
     */
    public float getRotationAccelerationX();

    /**
     * rotation acceleration
     */
    public void setRotationAccelerationX(float ax);

    public float getRotationAccelerationY();

    public void setRotationAccelerationY(float ay);

    public float getRotationAccelerationZ();

    public void setRotationAccelerationZ(float az);
}
