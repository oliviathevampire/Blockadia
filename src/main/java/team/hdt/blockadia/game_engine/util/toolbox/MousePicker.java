package team.hdt.blockadia.game_engine.util.toolbox;

import team.hdt.blockadia.game_engine.client.ClientMain;
import team.hdt.blockadia.game_engine.common.CameraInterface;
import team.hdt.blockadia.test.game.Main;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors4f;
import team.hdt.blockadia.game_engine.common.world.World;

public class MousePicker {

	private static final int RECURSION_COUNT = 40;
	private static final float RAY_RANGE = 120;
	private static final float RAY_SECTION = 2;
	private static final int NO_POINT = -1;

	private Vectors3f currentRay = new Vectors3f();

	private Matrix4fs viewMatrix;
	private CameraInterface camera;

	private Vectors3f currentTerrainPoint;
	private Vectors3f currentOffsetPoint;
	private boolean pickCenterScreen;
	private float offsetPointHeight;
	private float terrainDistance;

	private boolean rayUpToDate = false;
	private boolean terrainPointUpToDate = false;
	private boolean offsetPointUpToDate = false;
	private float offset = 0;
	private boolean testWater = false;

	public MousePicker(CameraInterface cam, boolean pickCenter) {
		camera = cam;
		viewMatrix = camera.getViewMatrix();
		this.pickCenterScreen = pickCenter;
	}
	
	public MousePicker(CameraInterface cam, boolean pickCenter, boolean water) {
		camera = cam;
		this.testWater = water;
		viewMatrix = camera.getViewMatrix();
		this.pickCenterScreen = pickCenter;
	}

	public Vectors3f getCurrentTerrainPoint() {
		if (!terrainPointUpToDate) {
			updateTerrainPoint();
		}
		return currentTerrainPoint;
	}
	
	public float getTerrainDistance(){
		if (!terrainPointUpToDate) {
			updateTerrainPoint();
		}
		return terrainDistance;
	}
	
	public Vectors3f getIntersectionWithPlane(float planeHeight){
		float d = -camera.getPosition().y / currentRay.y;
		return this.getPointOnRay(currentRay, d);
	}
	
	public void setOffsetPointHeight(float height){
		this.offsetPointHeight = height;
	}

	public Vectors3f getCurrentOffsetPoint() {
		if (!offsetPointUpToDate) {
			updateOffsetPoint();
		}
		return currentOffsetPoint;
	}
	
	public Vectors3f getRayPoint(float disFromCam){
		return getPointOnRay(getCurrentRay(), disFromCam);
	}

	public Vectors3f getCurrentRay() {
		if (!rayUpToDate) {
			updateMouseRay();
		}
		return currentRay;
	}

	/**
	 * Indicates that the current aim ray and picked terrain points may no
	 * longer be correct because the camera has been updated since they were
	 * last calculated.
	 */
	public void update() {
		viewMatrix = camera.getViewMatrix();
		rayUpToDate = false;
		terrainPointUpToDate = false;
		offsetPointUpToDate = false;
	}

	private void updateTerrainPoint() {
		offset = 0;
		Vectors3f ray = getCurrentRay();
		float section = getSectionID(ray, testWater);
		if (section == NO_POINT) {
			currentTerrainPoint = null;
			return;
		}
		currentTerrainPoint = binarySearch(0, section * RAY_SECTION, (section + 1) * RAY_SECTION, ray, testWater);
		terrainPointUpToDate = true;
	}

	private void updateOffsetPoint() {
		offset = offsetPointHeight;
		Vectors3f ray = getCurrentRay();
		float section = getSectionID(ray, true);
		if (section == NO_POINT) {
			currentTerrainPoint = null;
			return;
		}
		currentOffsetPoint = binarySearch(0, section * RAY_SECTION, (section + 1) * RAY_SECTION, ray, true);
		offsetPointUpToDate = true;
	}

	private void updateMouseRay() {
		float mouseX = (float) ClientMain.display.getMouseX();
		float mouseY = (float) ClientMain.display.getMouseY();
		Vectors2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
		if (pickCenterScreen) {
			normalizedCoords.set(0, -0f);
		}
		Vectors4f clipCoords = new Vectors4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		Vectors4f eyeCoords = toEyeCoords(clipCoords);
		Vectors3f worldRay = toWorldCoords(eyeCoords);
		this.currentRay = worldRay;
		rayUpToDate = true;
	}

	private Vectors3f toWorldCoords(Vectors4f eyeCoords) {
		Matrix4fs invertedView = Matrix4fs.invert(viewMatrix, null);
		Vectors4f rayWorld = Matrix4fs.transform(invertedView, eyeCoords, null);
		Vectors3f mouseRay = new Vectors3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private Vectors4f toEyeCoords(Vectors4f clipCoords) {
		Matrix4fs invertedProjection = Matrix4fs.invert(new Matrix4fs(), null);
		Vectors4f eyeCoords = Matrix4fs.transform(invertedProjection, clipCoords, null);
		return new Vectors4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vectors2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
		float x = (2.0f * mouseX) / Main.WIDTH - 1f;
		float y = (2.0f * mouseY) / Main.HEIGHT - 1f;
		return new Vectors2f(x, y);
	}

	// **********************************************************

	private int getSectionID(Vectors3f ray, boolean testWater) {
		for (int i = 0; i < RAY_RANGE / RAY_SECTION; i++) {
			if (intersectionInRange(i * RAY_SECTION, (i + 1) * RAY_SECTION, ray, testWater)) {
				return i;
			}
		}
		return NO_POINT;
	}

	private Vectors3f getPointOnRay(Vectors3f ray, float distance) {
		Vectors3f camPos = camera.getPosition();
		Vectors3f start = new Vectors3f(camPos.x, camPos.y, camPos.z);
		Vectors3f scaledRay = new Vectors3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vectors3f.add(start, scaledRay, null);
	}

	private Vectors3f binarySearch(int count, float start, float finish, Vectors3f ray, boolean testWater) {
		float half = start + ((finish - start) / 2f);
		if (count >= RECURSION_COUNT) {
			Vectors3f endPoint = getPointOnRay(ray, half);
			World terrain = getTerrain();
			if (terrain != null) {
				terrainDistance = half;
				return endPoint;
			} else {
				terrainDistance = 0;
				return null;
			}
		}
		if (intersectionInRange(start, half, ray, testWater)) {
			return binarySearch(count + 1, start, half, ray, testWater);
		} else {
			return binarySearch(count + 1, half, finish, ray, testWater);
		}
	}

	private boolean intersectionInRange(float start, float finish, Vectors3f ray, boolean testWater) {
		Vectors3f startPoint = getPointOnRay(ray, start);
		Vectors3f endPoint = getPointOnRay(ray, finish);
		/*if (!isUnderGround(startPoint, testWater) && isUnderGround(endPoint, testWater)) {
			return true;
		} else {
			return false;
		}*/
		return false;
	}

	/*private boolean isUnderGround(Vectors3f testPoint, boolean testWater) {
		World terrain = getTerrain();
		float height = 0;
		if (terrain != null && terrain.isOnWorld(testPoint)) {
			height = terrain.getHeightOfTerrain(testPoint.getX(), testPoint.getZ());
			if(testWater){
				height = Math.max(GameManager.getWorld().getWaterHeight(), height);
			}
		} else {
			return false;
		}
		if (testPoint.y < height + offset) {
			return true;
		} else {
			return false;
		}
	}*/

	private World getTerrain() {
		return new World();
	}

}
