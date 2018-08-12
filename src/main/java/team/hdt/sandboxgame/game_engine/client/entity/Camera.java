package team.hdt.sandboxgame.game_engine.client.entity;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

public class Camera {

    private float distanceFromPlayer = 60;
    private float angleAroundPlayer = 0;

    private Vectors3f position = new Vectors3f(-300, 500, 400);
    private float pitch = 20;
    private float yaw = 120;
    private float roll = 10;

    //private Player player;

    public Camera(/*Player player*/) {

    }

    public void move() {
        calculateZoom();
        calculatePitch();
        cameraMove();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
    }

    public Vectors3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void cameraMove() {

    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {

    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    public void calculateZoom() {

    }

    public void calculatePitch() {

    }

    private void calculateAngleAroundPlayer() {

    }
}
