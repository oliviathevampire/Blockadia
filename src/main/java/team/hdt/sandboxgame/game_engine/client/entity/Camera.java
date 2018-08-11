package team.hdt.sandboxgame.game_engine.client.entity;

import team.hdt.sandboxgame.game_engine.util.math.vectors.Vectors3f;

public class Camera {
        private float distanceFromPlayer = 60;
        private float anlgeAroundPlayer = 0;

        private Vectors3f position = new Vectors3f(- 300, 500, 400);
        private float pitch = 20;
        private float yaw = 120;
        private float roll = 10;

        //private Player player;


        public Camera(/*Player player*/) {

        }

        public void move() {
            calculateZoom();
            calculatePitch();
            cammove();
            calculateAngleAroundPlayer();
            float horizontalDistance = calcualateHorizontalDistance();
            float verticalDistance = calcualateVerticalDistance();
            calculateCamaraPosition(horizontalDistance, verticalDistance);

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

        private void cammove() {

        }

        private void calculateCamaraPosition(float horizontalDistance, float verticalDistance) {

        }

        private float calcualateHorizontalDistance() {
            return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
        }

        private float calcualateVerticalDistance() {
            return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
        }

        public void calculateZoom() {

        }

        public void calculatePitch() {

        }

        private void calculateAngleAroundPlayer() {

        }
}
