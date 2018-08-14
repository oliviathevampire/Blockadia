package team.hdt.blockadia.game_engine.common;

import team.hdt.blockadia.game_engine.client.ClientMain;
import team.hdt.blockadia.game_engine.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine.common.gameManaging.GameManager;
import team.hdt.blockadia.game_engine.common.gameManaging.GameState;
import team.hdt.blockadia.game_engine.common.util.math.interpolation.SmoothFloat;
import team.hdt.blockadia.game_engine.common.util.math.interpolation.SmoothVector;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.util.BinaryReader;
import team.hdt.blockadia.game_engine.util.BinaryWriter;
import team.hdt.blockadia.game_engine.util.toolbox.Maths;

public class Camera2 implements IGameCam {

    public static final float FIELD_OF_VIEW = 35f;
    private static final Vectors3f STANDARD_POS = new Vectors3f(109f, 9.54f, 88f);
    private static final float STANDARD_PITCH = 45;
    private final static float MIN_ZOOM = 1f;
    private final static float MAX_ZOOM = 140;
    private final static float HEIGHT_OFFSET = 0.4f;
    private final static float MIN_PITCH = 5;
    private final static float MAX_PITCH = 85;
    private static final float TO_TARGET_AGILITY = 8;
    private final static float LOW_ALTITUDE = 0.3f;
    private final static float HIGH_ALTITUDE = 0.9f;
    private final static float ENTITY_ZOOM = 5;
    private final static float RELEASE_ZOOM = 4;
    private static final float ZOOM_DISTANCE_FACTOR = 0.07f;
    private final static float MAX_YAW_CHANGE = 12;
    private final static float MAX_PITCH_CHANGE = 7;
    private static final float SCROLL_SPEED = 0.25f;
    private static final float KEY_SCROLL_FACTOR = 4f;
    private final static float ROTATION_FACTOR = 33.33333f;
    private final static float ZOOM_SPEED = 0.4f;
    private final static float LISTENER_OFFSET = 4f;
    private static final float ANIM_TIME = 4;
    private final float NEAR_PLANE = 0.15f;
    private final float FAR_PLANE = 2000f;// TODO link with something
    private SmoothVector position = new SmoothVector(new Vectors3f(), 12f);
    private SmoothFloat yaw = new SmoothFloat(0, 12);
    private SmoothFloat pitch = new SmoothFloat(0, 10);
    private Vectors3f target = new Vectors3f();
    private SmoothFloat distanceFromTarget = new SmoothFloat(0, 8);
    private Vectors3f entityPos = null;
    private Vectors3f followTarget = new Vectors3f();
    private boolean oneOffTarget = false;
    private boolean doingStartAnimation = false;
    private boolean animationStarted = false;
    private float animProgress = 0;
    private boolean updatePosition = false;
    private boolean newStart = true;
    private boolean targetMoved = false;
    private boolean zoomOccurred = false;
    private boolean enabled = true;
    private float distanceBeforeScroll = 5;
    private boolean reflected = false;
    private Matrix4fs viewMatrix = new Matrix4fs();
    private Vectors3f reflectedPosition = new Vectors3f();
    private Matrix4fs reflectedViewMatrix = new Matrix4fs();

    public Camera2() {

    }

    @Override
    public void moveCamera() {
        updatePosition = false;
        checkMouseState();
        checkZoomInput();
        checkRotationInput();
        if (entityPos != null) {
            followEntity();
        }

        if (updatePosition) {
            recalculatePosition();
        }
        if (entityPos == null || oneOffTarget) {
            translateCamera();
            if (targetMoved) {
                if (oneOffTarget) {
                    oneOffTarget = false;
                    entityPos = null;
                }
                updateTargetPoint();
            }
        }

        if (targetMoved || updatePosition) {
            newStart = false;
            doingStartAnimation = false;
        }
        if (newStart && !doingStartAnimation) {
            updateTargetPoint();
        }
        if (doingStartAnimation && !animationStarted) {
            animationStarted = true;
            updateTargetPoint();
        }
        recalculateViewMatrix();
    }

    @Override
    public Vectors3f getPosition() {
        if (reflected) {
            return reflectedPosition;
        } else {
            return position.get();
        }
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
    public float getFOV() {
        return FIELD_OF_VIEW;
    }

    @Override
    public Matrix4fs getViewMatrix() {
        if (reflected) {
            return reflectedViewMatrix;
        } else {
            return viewMatrix;
        }
    }

    @Override
    public void reflect(float waterHeight) {
        reflected = !reflected;
        if (reflected) {
            this.reflectedPosition.set(position.get());
            reflectedPosition.y -= 2 * (reflectedPosition.y - waterHeight);
            recalculateReflectedViewMatrix();
        }
    }

    @Override
    public float getPitch() {
        if (reflected) {
            return -pitch.get();
        } else {
            return pitch.get();
        }
    }

    @Override
    public float getYaw() {
        return yaw.get();
    }

    @Override
    public float getAimDistance() {
        return distanceFromTarget.get();
    }

    @Override
    public void loadState(BinaryReader reader) throws Exception {
        this.position.force(reader.readVector());
        this.yaw.force(reader.readFloat());
        this.pitch.force(reader.readFloat());
        // reader.readFloat();
        newStart = true;
    }

    @Override
    public void saveState(BinaryWriter writer) {
        writer.writeVector(position.get());
        writer.writeFloat(yaw.get() % 360);
        writer.writeFloat(pitch.get());
    }

    @Override
    public void resetPosition() {
        this.position.force(STANDARD_POS);
        this.pitch.force(7.2f);
        this.yaw.force(64);
        doingStartAnimation = true;
        animationStarted = false;
        animProgress = 0;
        newStart = true;
        recalculateViewMatrix();
    }

    @Override
    public void focusOn(Vectors3f point) {
        this.entityPos = point;
        oneOffTarget = true;
        position.cancelTarget();
    }

    @Override
    public void enable(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setTargetEntity(Vectors3f targetPosition) {
        this.entityPos = targetPosition;
        if (targetPosition != null) {
            distanceFromTarget.setTarget(ENTITY_ZOOM);
            oneOffTarget = false;
        } else {
            distanceFromTarget.increaseTarget(RELEASE_ZOOM);
        }
    }

    private void followEntity() {
		/*float height = GameManager.getWorld().getHeightOfTerrain(entityPos.x, entityPos.z);
		float altitude = entityPos.y - height;
		float value = Maths.smoothStep(LOW_ALTITUDE, HIGH_ALTITUDE, altitude);
		float targetY = Maths.interpolate(height, entityPos.y, value);
		followTarget.set(entityPos.x, targetY, entityPos.z);*/
        updateTargetPosition();
        updatePosition = true;
    }

    private void translateCamera() {
        this.targetMoved = false;
        Vectors2f speeds = getSpeedFromInputs();
        if (targetMoved) {
            float forwardDistance = speeds.x * ClientMain.getDeltaSeconds();
            float sideDistance = speeds.y * ClientMain.getDeltaSeconds();
            float dx = (float) (forwardDistance * Math.sin(Math.toRadians(yaw.get())));
            float dz = (float) (forwardDistance * Math.cos(Math.toRadians(yaw.get())));
            float sideDx = (float) (sideDistance * Math.sin(Math.toRadians(yaw.get() + 90)));
            float sideDz = (float) (sideDistance * Math.cos(Math.toRadians(yaw.get() + 90)));
            position.increaseTarget(dx + sideDx, 0, dz + sideDz);
            Vectors3f posTarget = position.getTarget();
			/*float height = GameManager.getWorld().getHeightOfTerrain(posTarget.x, posTarget.z);
			if (posTarget.y < height + HEIGHT_OFFSET) {
				posTarget.y = height + HEIGHT_OFFSET;
			}*/
        }
        position.update(ClientMain.getDeltaSeconds());
    }

    private void updateTargetPoint() {
		/*Vectors3f terrainPoint = cameraAimer.getCurrentTerrainPoint();
		if (terrainPoint == null && GameManager.sessionManager.hasWorldReady()
				&& !GameManager.sessionManager.isLoading()) {
			terrainPoint = cameraAimer.getIntersectionWithPlane(0);
		}
		if (terrainPoint != null) {
			this.target.set(terrainPoint);
			this.distanceFromTarget.forceOnlyActualValue(Vectors3f.sub(target, position.get(), null).length());
		}*/
    }

    private void updateTargetPosition() {
        Vectors3f offset = Vectors3f.sub(followTarget, target, null);
        offset.scale(ClientMain.getDeltaSeconds() * TO_TARGET_AGILITY);
        Vectors3f.add(target, offset, target);
    }

    private void checkZoomInput() {
        zoomOccurred = false;
        /*MyMouse mouse = MyMouse.getActiveMouse();*/
        float wheel = 0;
        /*if (inputsActive()) {
			wheel = mouse.getDWheelSigned();
			if(wheel!=0){
				animationStarted = false;
			}
		}
		if(MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_X)){
			wheel = 0.005f;
		}*/
        if (animationStarted) {
            animProgress += ClientMain.getDeltaSeconds();
            float factor = 1f - animProgress / ANIM_TIME;
            if (GameManager.getGameState() == GameState.GAME_MENU) {
                animProgress = 0;
            }
            wheel = 0.008f * factor;
            if (distanceFromTarget.get() < 15 || animProgress >= ANIM_TIME) {
                animationStarted = false;
            }
        }
        float zoomLevel = wheel * ZOOM_SPEED;
        float extra = distanceFromTarget.get() * wheel * ZOOM_DISTANCE_FACTOR;
        zoomLevel += extra;

        if (zoomLevel != 0) {
            zoomOccurred = true;
            updatePosition = true;
            this.distanceFromTarget.increaseTarget(-zoomLevel);
            distanceFromTarget.clampTarget(MIN_ZOOM, MAX_ZOOM);
        }
        distanceFromTarget.update(ClientMain.getDeltaSeconds());
        if (!distanceFromTarget.reached()) {
            updatePosition = true;
        }
    }

    private void checkRotationInput() {
		/*MyMouse mouse = MyMouse.getActiveMouse();
		if ((mouse.isMouseWheelDown() || MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_V)
				|| (mouse.isRightButtonDown() && MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_LCONTROL)))
				&& !targetMoved) {
			this.updatePosition = true;
			animationStarted = false;
			position.cancelTarget();
			float pitchChange = 0;
			if (inputsActive()) {
				pitchChange = -((float) mouse.getDY()) * DisplayManager.getDeltaSeconds() * ROTATION_FACTOR;
			}
			pitchChange = Maths.clamp(pitchChange, -MAX_PITCH_CHANGE, MAX_PITCH_CHANGE);
			this.pitch.increaseTarget(pitchChange);
			pitch.clampTarget(MIN_PITCH, MAX_PITCH);
			float yawChange = 0;
			if (inputsActive()) {
				yawChange = -((float) mouse.getDX()) * DisplayManager.getDeltaSeconds() * ROTATION_FACTOR;
			}
			if (MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_V)) {
				yawChange = -0.07f;
			}
			yawChange = Maths.clamp(yawChange, -MAX_YAW_CHANGE, MAX_YAW_CHANGE);
			this.yaw.increaseTarget(yawChange);
		} else if (targetMoved) {
			yaw.cancelTarget();
			pitch.cancelTarget();
		}*/
        yaw.update(ClientMain.getDeltaSeconds());
        pitch.update(ClientMain.getDeltaSeconds());
        if (!yaw.reached() || !pitch.reached()) {
            updatePosition = true;
        }
    }

    private void recalculatePosition() {
        Vectors3f offset = new Vectors3f();
        float pitchRadians = (float) Math.toRadians(pitch.get());
        offset.y = (float) (distanceFromTarget.get() * Math.sin(pitchRadians));
        float horizDistance = (float) (distanceFromTarget.get() * Math.cos(pitchRadians));
        float yawRadians = (float) Math.toRadians(yaw.get());
        offset.x = (float) (horizDistance * Math.sin(yawRadians));
        offset.z = (float) (horizDistance * Math.cos(yawRadians));
        Vectors3f pos = Vectors3f.add(target, offset, null);

		/*float height = GameManager.getWorld().getHeightOfTerrain(pos.x, pos.z);
		if (pos.y < height + HEIGHT_OFFSET) {
			pos.y = height + HEIGHT_OFFSET;
		}*/

        position.forceOnlyActualValue(pos);
    }

    private Vectors2f getSpeedFromInputs() {
        Vectors2f speeds = new Vectors2f();
		/*MyKeyboard input = MyKeyboard.getKeyboard();
		if (MyMouse.getActiveMouse().isRightButtonDown() && inputsActive()
				&& !MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_LCONTROL)) {
			speeds.x = MyMouse.getActiveMouse().getDY() * SCROLL_SPEED;
			speeds.y = -MyMouse.getActiveMouse().getDX() * SCROLL_SPEED;
			targetMoved = true;
			animationStarted = false;
		}
		if (input.isKeyDown(Keyboard.KEY_RIGHT) || input.isKeyDown(Keyboard.KEY_D)) {
			targetMoved = true;
			animationStarted = false;
			speeds.y = SCROLL_SPEED * KEY_SCROLL_FACTOR;
		} else if (input.isKeyDown(Keyboard.KEY_LEFT) || input.isKeyDown(Keyboard.KEY_A)) {
			speeds.y = -SCROLL_SPEED * KEY_SCROLL_FACTOR;
			targetMoved = true;
			animationStarted = false;
		} else if (input.isKeyDown(Keyboard.KEY_C)) {
			speeds.y = -0.25f;
			targetMoved = true;
			animationStarted = false;
		}
		if (input.isKeyDown(Keyboard.KEY_UP) || input.isKeyDown(Keyboard.KEY_W)) {
			targetMoved = true;
			animationStarted = false;
			speeds.x = -SCROLL_SPEED * KEY_SCROLL_FACTOR;
		} else if (input.isKeyDown(Keyboard.KEY_DOWN) || input.isKeyDown(Keyboard.KEY_S)) {
			targetMoved = true;
			animationStarted = false;
			speeds.x = SCROLL_SPEED * KEY_SCROLL_FACTOR;
		}*/
        float actualDis = 0.5f + distanceBeforeScroll / 2f;
        speeds.x *= actualDis;
        speeds.y *= actualDis;
        return speeds;
    }

    private void checkMouseState() {
		/*MyMouse mouse = MyMouse.getActiveMouse();
		if (mouse.isMiddleClick() || mouse.isRightClick()) {
			GameManager.gameState.suggestState(GameState.CAMERA);
		} else if ((mouse.isMiddleClickRelease() && !mouse.isRightButtonDown())
				|| (mouse.isRightClickRelease() && !mouse.isMouseWheelDown())) {
			GameManager.gameState.endState(GameState.CAMERA);
		}*/
    }

    private void recalculateViewMatrix() {
        viewMatrix.setIdentity();
        Vectors3f pos = position.get();
        Vectors3f cameraPos = new Vectors3f(-pos.x, -pos.y, -pos.z);
        Matrix4fs.rotate(Maths.degreesToRadians(pitch.get()), new Vectors3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4fs.rotate(Maths.degreesToRadians(-yaw.get()), new Vectors3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4fs.translate(cameraPos, viewMatrix, viewMatrix);
    }

    private void recalculateReflectedViewMatrix() {
        reflectedViewMatrix.setIdentity();
        Vectors3f cameraPos = new Vectors3f(-reflectedPosition.x, -reflectedPosition.y, -reflectedPosition.z);
        Matrix4fs.rotate(Maths.degreesToRadians(-pitch.get()), new Vectors3f(1, 0, 0), reflectedViewMatrix, reflectedViewMatrix);
        Matrix4fs.rotate(Maths.degreesToRadians(-yaw.get()), new Vectors3f(0, 1, 0), reflectedViewMatrix, reflectedViewMatrix);
        Matrix4fs.translate(cameraPos, reflectedViewMatrix, reflectedViewMatrix);
    }

    public boolean inputsActive() {
        return enabled && !GuiMaster.isMouseInGui() && GameManager.getGameState() != GameState.GAME_MENU
                && GameManager.getGameState() != GameState.SPLASH_SCREEN;
    }

}
