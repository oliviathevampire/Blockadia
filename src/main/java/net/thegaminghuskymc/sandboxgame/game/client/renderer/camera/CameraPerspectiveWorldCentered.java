package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;

/**
 * a camera which follow the given entity, 3rd perso view
 */
public class CameraPerspectiveWorldCentered extends CameraPerspectiveWorld {

    private Vector3f center;

    /**
     * distance from the entity
     */
    private float distanceFromCenter;

    /**
     * angle around the entity
     */
    private float angleAroundCenter;
    private Vector3f vecbuffer = new Vector3f();

    public CameraPerspectiveWorldCentered(GLFWWindow window) {
        super(window);
        this.center = new Vector3f();
        this.distanceFromCenter = 16;
        this.angleAroundCenter = 0;
        this.increasePitch(15);
    }

    @Override
    public void update() {
        super.update();
        this.calculateCameraPosition();
    }

    public double getAngleAroundCenter() {
        return (this.angleAroundCenter);
    }

    public void setAngleAroundCenter(float angle) {
        this.angleAroundCenter = angle;
    }

    public void increaseAngleAroundCenter(float inc) {
        this.angleAroundCenter += inc;
    }

    public void increaseDistanceFromCenter(float inc) {
        this.distanceFromCenter += inc;
    }

    public void setCenter(float x, float y, float z) {
        this.center.set(x, y, z);
    }

    public float getDistanceFromCenter() {
        return (this.distanceFromCenter);
    }

    public void setDistanceFromCenter(float distance) {
        this.distanceFromCenter = distance;
    }

    public Vector3f getCenter() {
        return (this.center);
    }

    public void setCenter(Vector3f center) {
        this.center.set(center);
    }

    private void calculateCameraPosition() {
        Vector3f center = vecbuffer.set(this.center);

        double horizontal = (this.distanceFromCenter * Math.cos(Math.toRadians(this.getPitch())));
        double vertical = (this.distanceFromCenter * Math.sin(Math.toRadians(this.getPitch())));
        double angle = this.angleAroundCenter;
        float offx = (float) (horizontal * Math.sin(Math.toRadians(angle)));
        float offz = (float) (horizontal * Math.cos(Math.toRadians(angle)));

        float x = center.x - offx;
        float y = (float) (center.y + vertical);
        float z = center.z - offz;

        super.setPosition(x, y, z);
        super.setYaw((float) (180 - (this.angleAroundCenter)));
    }
}
