package team.hdt.blockadia.game_engine_old.common;

import team.hdt.blockadia.game_engine_old.client.ClientMain;
import team.hdt.blockadia.game_engine_old.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine_old.common.util.math.Maths;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine_old.util.BinaryReader;
import team.hdt.blockadia.game_engine_old.util.BinaryWriter;

/**
 * Represents the in-game camera and handles all of the camera movement and
 * controls.
 *
 * @author Karl
 */
public class Camera implements IGameCam {

    public static final float FIELD_OF_VIEW = 35f;
    private static final float STANDARD_PITCH = 0.6f;
    private static final float STANDARD_ZOOM = 15;
    private static final Vectors3f STANDARD_POS = new Vectors3f(/*World.SIZE / 2, 0, World.SIZE / 2*/ 0, 0, 0);
    private static final float PITCH_AGILITY = 8f;
    private static final float CONTROL_DIS = 8f;
    private static final float ZOOM_AGILITY = 4f;
    private static final float ZOOM_DISTANCE_FACTOR = 0.05f;
    private static final float ROTATE_AGILITY = 6f;
    private final static int INFLUENCE_OF_MOUSEDY = 400;
    private final static int INFLUENCE_OF_MOUSEDX = 3;
    private final static float INFLUENCE_OF_MOUSE_WHEEL = 0.6f;
    private final static float MAX_ANGLE_OF_ELEVATION = 1.5f;
    private final static float PITCH_OFFSET = 0f;
    private final static float MINIMUM_ZOOM = 0;
    private final static float MAXIMUM_ZOOM = 300;
    private final static float MAX_HORIZONTAL_CHANGE = 500;
    private final static float MAX_VERTICAL_CHANGE = 5;
    private final static float MIN_SCROLL = 1.7f;
    private static final float SCROLL_SPEED = 0.18f;
    private static final float SCROLL_AGILITY = 10f;
    private static final float FOCUS_AGILITY = 2f;
    private static final IGameCam currentCamera = new Camera2();
    private final float NEAR_PLANE = 0.15f;
    private final float FAR_PLANE = 2000f;// TODO link with something
    private Matrix4fs viewMatrix = new Matrix4fs();
    private Vectors3f position = new Vectors3f();
    private float horizontalDistanceFromPlayer;
    private float verticalDistanceFromPlayer;
    private float pitch;
    private float yaw;
    private boolean normalMode = true;
    private Vectors3f target = new Vectors3f(STANDARD_POS);
    private Vectors3f aimingAt = new Vectors3f(target);
    private float actualDistanceFromPoint = STANDARD_ZOOM;
    private float targetZoom = actualDistanceFromPoint;
    private float targetElevation = STANDARD_PITCH;
    private float angleOfElevation = targetElevation;
    private float targetRotationAngle = 0;
    private float angleAroundPlayer = targetRotationAngle;
    private float zoomChange;
    private boolean enabled = true;

    private Camera() {
        calculateDistances();
    }

    /**
     * @return The camera being used in the scene.
     */
    public static IGameCam getCamera() {
        return currentCamera;
    }

    private static void createViewMatrix(Matrix4fs viewMatrix, Vectors3f position, float pitch, float yaw) {
        viewMatrix.setIdentity();
        Vectors3f cameraPos = new Vectors3f(-position.x, -position.y, -position.z);
        Matrix4fs.rotate(Maths.degreesToRadians(pitch), new Vectors3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4fs.rotate(Maths.degreesToRadians(-yaw), new Vectors3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4fs.translate(cameraPos, viewMatrix, viewMatrix);
    }

    @Override
    public void loadState(BinaryReader reader) throws Exception {
        this.target.set(reader.readVector());
        this.targetZoom = reader.readFloat();
        this.targetElevation = reader.readFloat();
        this.targetRotationAngle = reader.readFloat();
        setToTarget();
    }

    @Override
    public void saveState(BinaryWriter writer) {
        writer.writeVector(target);
        writer.writeFloat(targetZoom);
        writer.writeFloat(targetElevation);
        writer.writeFloat(targetRotationAngle);
    }

    @Override
    public void resetPosition() {
        this.target.set(STANDARD_POS);
        this.targetZoom = STANDARD_ZOOM;
        this.targetElevation = STANDARD_PITCH;
        this.targetRotationAngle = 0;
        setToTarget();
    }

    @Override
    public void focusOn(Vectors3f point) {
        float offset = (float) (point.y / Math.tan(angleOfElevation));
        double radians = Math.toRadians(yaw);
        float dX = (float) (offset * Math.sin(radians));
        float dZ = (float) (offset * Math.cos(radians));
        target.set(point.x - dX, 0, point.z - dZ);
    }

    @Override
    public void enable(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return The distance from the point which the camera is focussed on.
     */
    private float getZoom() {
        return actualDistanceFromPoint;
    }

    @Override
    public void moveCamera() {
        checkMouseState();
        moveTarget();
        if (enabled && !GuiMaster.isMouseInGui()) {
            calculateHorizontalAngle();
            calculateVerticalAngle();
            calculateZoom();
        }
        updateTargetPosition();
        updateActualZoom();
        updateHorizontalAngle();
        updatePitchAngle();
        calculateDistances();
        calculatePosition();
        createViewMatrix(viewMatrix, position, pitch, yaw);
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public float getAimDistance() {
        return 0;
    }

    @Override
    public Vectors3f getPosition() {
        return position;
    }

    @Override
    public float getFOV() {
        return FIELD_OF_VIEW;
    }

    @Override
    public float getNearPlane() {
        return NEAR_PLANE;
    }

    @Override
    public float getFarPlane() {
        return FAR_PLANE;
    }

    @Override
    public Matrix4fs getViewMatrix() {
        return viewMatrix;
    }

    @Override
    public void reflect(float waterHeight) {
        position.y -= 2 * (position.y - waterHeight);
        pitch = -pitch;
        createViewMatrix(viewMatrix, position, pitch, yaw);
    }

    private void setToTarget() {
        this.aimingAt.set(target);
        this.actualDistanceFromPoint = targetZoom;
        this.angleOfElevation = targetElevation;
        this.angleAroundPlayer = targetRotationAngle;
    }

    /**
     * Smoothly moves around the aim point of the camera based on the mouse
     * input.
     */
    private void moveTarget() {
        if (!normalMode) {
            return;
        }
        float speed = 0;
        float sideSpeed = 0;
        float actualDis = Math.max(actualDistanceFromPoint, MIN_SCROLL);
		/*if (mouse.isRightButtonDown()) {
			speed = MyMouse.getActiveMouse().getDY() * actualDis * SCROLL_SPEED;
			sideSpeed = -MyMouse.getActiveMouse().getDX() * actualDis * SCROLL_SPEED;
		}
		float zoomAmount = Math.max(this.getZoom(), MIN_SCROLL);
		if (MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)||MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_D)) {
			sideSpeed = zoomAmount * SCROLL_SPEED * 2.5f;
		} else if (MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)||MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_A)) {
			sideSpeed = -zoomAmount * SCROLL_SPEED * 2.5f;
		}
		if (MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_UP)||MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_W)) {
			speed = -zoomAmount * SCROLL_SPEED * 2.5f;
		} else if (MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_DOWN)||MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_S)) {
			speed = zoomAmount * SCROLL_SPEED * 2.5f;
		}*/
        float distance = speed * ClientMain.getDeltaSeconds();
        float sideDistance = sideSpeed * ClientMain.getDeltaSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(yaw)));
        float dz = (float) (distance * Math.cos(Math.toRadians(yaw)));
        float sideDx = (float) (sideDistance * Math.sin(Math.toRadians(yaw + 90)));
        float sideDz = (float) (sideDistance * Math.cos(Math.toRadians(yaw + 90)));
        target.x += dx + sideDx;
        target.y = 0;
        target.z += dz + sideDz;
    }

    private void updateTargetPosition() {
        Vectors3f offset = Vectors3f.sub(target, aimingAt, null);
        offset.scale(ClientMain.getDeltaSeconds() * SCROLL_AGILITY);
        Vectors3f.add(aimingAt, offset, aimingAt);
    }

    private void calculatePosition() {
        position.x = aimingAt.x
                - (float) (horizontalDistanceFromPlayer * Math.sin(Math.toRadians((double) angleAroundPlayer)));
        position.z = aimingAt.z
                - (float) (horizontalDistanceFromPlayer * Math.cos(Math.toRadians((double) angleAroundPlayer)));
        position.y = verticalDistanceFromPlayer + aimingAt.y;

        yaw = Maths.DEGREES_IN_HALF_CIRCLE + angleAroundPlayer;
        pitch = (float) Math.toDegrees(angleOfElevation) - PITCH_OFFSET;
    }

    private void calculateHorizontalAngle() {
        float delta = ClientMain.getDeltaSeconds();
		/*if ((mouse.isMouseWheelDown() && !keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				|| keyboard.isKeyDown(Keyboard.KEY_C)) {
			float angleChange = ((float) mouse.getDX()) / INFLUENCE_OF_MOUSEDX;
			if (keyboard.isKeyDown(Keyboard.KEY_C)) {
				angleChange = DisplayManager.getDeltaSeconds() * 10;
			}
			if (angleChange > MAX_HORIZONTAL_CHANGE * delta) {
				angleChange = MAX_HORIZONTAL_CHANGE * delta;
			} else if (angleChange < -MAX_HORIZONTAL_CHANGE * delta) {
				angleChange = -MAX_HORIZONTAL_CHANGE * delta;
			}
			targetRotationAngle -= angleChange;
			if (targetRotationAngle >= Maths.DEGREES_IN_HALF_CIRCLE) {
				targetRotationAngle -= Maths.DEGREES_IN_CIRCLE;
			} else if (targetRotationAngle <= -Maths.DEGREES_IN_HALF_CIRCLE) {
				targetRotationAngle += Maths.DEGREES_IN_CIRCLE;
			}
		}*/
    }

    private void updateHorizontalAngle() {
        float offset = targetRotationAngle - angleAroundPlayer;
        if (Math.abs(offset) > Maths.DEGREES_IN_HALF_CIRCLE) {
            if (offset < 0) {
                offset = (targetRotationAngle + Maths.DEGREES_IN_CIRCLE) - angleAroundPlayer;
            } else {
                offset = (targetRotationAngle - Maths.DEGREES_IN_CIRCLE) - angleAroundPlayer;
            }
        }
        float change = offset * ClientMain.getDeltaSeconds() * ROTATE_AGILITY;
        this.angleAroundPlayer += change;
        if (angleAroundPlayer >= Maths.DEGREES_IN_HALF_CIRCLE) {
            angleAroundPlayer -= Maths.DEGREES_IN_CIRCLE;
        } else if (angleAroundPlayer <= -Maths.DEGREES_IN_HALF_CIRCLE) {
            angleAroundPlayer += Maths.DEGREES_IN_CIRCLE;
        }
    }

    private void calculateVerticalAngle() {
        float delta = ClientMain.getDeltaSeconds();
		/*if (mouse.isMouseWheelDown() && !keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			float angleChange = ((float) mouse.getDY()) / INFLUENCE_OF_MOUSEDY;
			if (angleChange > MAX_VERTICAL_CHANGE * delta) {
				angleChange = MAX_VERTICAL_CHANGE * delta;
			} else if (angleChange < -MAX_VERTICAL_CHANGE * delta) {
				angleChange = -MAX_VERTICAL_CHANGE * delta;
			}
			targetElevation -= angleChange;
			if (targetElevation >= MAX_ANGLE_OF_ELEVATION) {
				targetElevation = MAX_ANGLE_OF_ELEVATION;
			} else if (targetElevation <= 0) {
				targetElevation = 0;
			}
		}*/
    }

    private void updatePitchAngle() {
        float offset = targetElevation - angleOfElevation;
        float change = offset * ClientMain.getDeltaSeconds() * PITCH_AGILITY;
        this.angleOfElevation += change;
    }

    private void calculateZoom() {
        if (!normalMode) {
            return;
        }
		/*float wheel = mouse.getDWheelSigned();
		float zoomLevel = wheel * INFLUENCE_OF_MOUSE_WHEEL;
		float extra = targetZoom * wheel * ZOOM_DISTANCE_FACTOR;
		zoomLevel += extra;
		// zoomLevel += targetZoom/5f;
		if (keyboard.isKeyDown(Keyboard.KEY_V)) {
			zoomLevel = DisplayManager.getDeltaSeconds() * 1;
		}
		if (zoomLevel != 0) {
			float maxZoom = MAXIMUM_ZOOM;
			targetZoom -= zoomLevel;
			if (targetZoom < MINIMUM_ZOOM) {
				targetZoom = MINIMUM_ZOOM;
			} else if (targetZoom > maxZoom) {
				targetZoom = maxZoom;
			}
		}*/
    }

    private void updateActualZoom() {
        float offset = targetZoom - actualDistanceFromPoint;
        zoomChange = offset * ClientMain.getDeltaSeconds() * ZOOM_AGILITY;
        this.actualDistanceFromPoint += zoomChange;
    }

    private void calculateDistances() {
        horizontalDistanceFromPlayer = (float) (actualDistanceFromPoint * Math.cos(angleOfElevation));
        verticalDistanceFromPlayer = (float) (actualDistanceFromPoint * Math.sin(angleOfElevation));
    }

    private void checkMouseState() {
		/*if (mouse.isMiddleClick() || mouse.isRightClick()) {
			GameManager.gameState.suggestState(GameState.CAMERA);
		} else if (mouse.isMiddleClickRelease() || mouse.isRightClickRelease()) {
			GameManager.gameState.endState(GameState.CAMERA);
		}*/
    }

    @Override
    public void setTargetEntity(Vectors3f entityPos) {

    }

}
