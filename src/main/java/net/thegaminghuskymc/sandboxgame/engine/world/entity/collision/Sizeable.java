package net.thegaminghuskymc.sandboxgame.engine.world.entity.collision;

public interface Sizeable {

    /**
     * @see Sizeable#resize(Sizeable, float)
     */
    public static void resize(Sizeable sizeable, double dt) {
        resize(sizeable, (float) dt);
    }

    /**
     * resize the sizeable objects depending on the elasped time 'dt' and set
     * acceleration and velocities
     *
     * @param sizeable : the sizeable object
     * @param dt       : elasped time
     */
    public static void resize(Sizeable sizeable, float dt) {
        sizeable.setSizeVelocityX(sizeable.getSizeVelocityX() + sizeable.getSizeAccelerationX() * dt);
        sizeable.setSizeVelocityY(sizeable.getSizeVelocityY() + sizeable.getSizeAccelerationY() * dt);
        sizeable.setSizeVelocityZ(sizeable.getSizeVelocityZ() + sizeable.getSizeAccelerationZ() * dt);
        sizeable.setSizeX(sizeable.getSizeX() + sizeable.getSizeVelocityX() * dt);
        sizeable.setSizeY(sizeable.getSizeY() + sizeable.getSizeVelocityY() * dt);
        sizeable.setSizeZ(sizeable.getSizeZ() + sizeable.getSizeVelocityZ() * dt);
    }

    /**
     * @return true if this sizeable objects is rotating
     */
    public static boolean isResizing(Sizeable sizeable) {
        return (sizeable.getSizeVelocityX() != 0 || sizeable.getSizeVelocityY() != 0
                || sizeable.getSizeVelocityZ() != 0);
    }

    /**
     * size
     */
    public float getSizeX();

    /**
     * size
     */
    public void setSizeX(float x);

    public float getSizeY();

    public void setSizeY(float y);

    public float getSizeZ();

    public void setSizeZ(float z);

    /**
     * size velocity
     */
    public float getSizeVelocityX();

    /**
     * size velocity
     */
    public void setSizeVelocityX(float vx);

    public float getSizeVelocityY();

    public void setSizeVelocityY(float vy);

    public float getSizeVelocityZ();

    public void setSizeVelocityZ(float vz);

    /**
     * size acceleration
     */
    public float getSizeAccelerationX();

    /**
     * size acceleration
     */
    public void setSizeAccelerationX(float ax);

    public float getSizeAccelerationY();

    public void setSizeAccelerationY(float ay);

    public float getSizeAccelerationZ();

    public void setSizeAccelerationZ(float az);
}
