package team.hdt.blockadia.engine.core_rewrite.object;

import org.joml.Vector3f;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;

public class Camera extends GLObject implements ICamera {

	@Override
	public Vector3f getPosition() {
		renderPosition.x = lastPosition.x + (position.x - lastPosition.x) * Blockadia.getInstance().getRenderPartialTicks();
		renderPosition.y = lastPosition.y + (position.y - lastPosition.y) * Blockadia.getInstance().getRenderPartialTicks();
		renderPosition.z = lastPosition.z + (position.z - lastPosition.z) * Blockadia.getInstance().getRenderPartialTicks();
		return renderPosition;
	}

	@Override
	public Vector3f getLastPosition() {
		return lastPosition;
	}

	@Override
	public Vector3f getRotation() {
		renderRotation.x = lastRotation.x + (rotation.x - lastRotation.x) * Blockadia.getInstance().getRenderPartialTicks();
		renderRotation.y = lastRotation.y + (rotation.y - lastRotation.y) * Blockadia.getInstance().getRenderPartialTicks();
		renderRotation.z = lastRotation.z + (rotation.z - lastRotation.z) * Blockadia.getInstance().getRenderPartialTicks();
		return renderRotation;
	}

	@Override
	public Vector3f getLastRotation() {
		return lastRotation;
	}

	public float getPitch() {
		return rotation.x;
	}

	public float getYaw() {
		return rotation.y;
	}

	public float getRoll() {
		return rotation.z;
	}
}