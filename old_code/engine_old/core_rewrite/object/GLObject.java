package team.hdt.blockadia.engine.core_rewrite.object;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.hdt.blockadia.engine.core_rewrite.util.Maths;

public class GLObject {

	private boolean insideFrustum;

	protected Vector3f renderPosition;
	protected Vector3f lastPosition;
	protected Vector3f position;

	protected Vector3f renderRotation;
	protected Vector3f lastRotation;
	protected Vector3f rotation;

	protected Vector3f renderScale;
	protected Vector3f lastScale;
	protected Vector3f scale;
	
	protected Vector3f direction;

	public GLObject() {
		this.renderPosition = new Vector3f();
		this.lastPosition = new Vector3f();
		this.position = new Vector3f();

		this.renderRotation = new Vector3f();
		this.lastRotation = new Vector3f();
		this.rotation = new Vector3f();

		this.renderScale = new Vector3f(1);
		this.lastScale = new Vector3f(1);
		this.scale = new Vector3f(1);
		
		this.direction = new Vector3f();
	}

	public void update() {
		this.lastPosition.set(this.position);
		this.lastRotation.set(this.rotation);
		this.lastScale.set(this.scale);
	}

	public void render(double mouseX, double mouseY, float partialTicks) {
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}
	
	public Vector3f getDirection() {
		return direction;
	}

	public Matrix4f getTransformation() {
		return Maths.createTransformationMatrix(position, rotation, scale);
	}

	public Matrix4f getRenderTransformation(float partialTicks) {
		renderPosition.x = lastPosition.x + (position.x - lastPosition.x) * partialTicks;
		renderPosition.y = lastPosition.y + (position.y - lastPosition.y) * partialTicks;
		renderPosition.z = lastPosition.z + (position.z - lastPosition.z) * partialTicks;

		renderRotation.x = lastRotation.x + (rotation.x - lastRotation.x) * partialTicks;
		renderRotation.y = lastRotation.y + (rotation.y - lastRotation.y) * partialTicks;
		renderRotation.z = lastRotation.z + (rotation.z - lastRotation.z) * partialTicks;

		renderScale.x = lastScale.x + (scale.x - lastScale.x) * partialTicks;
		renderScale.y = lastScale.y + (scale.y - lastScale.y) * partialTicks;
		renderScale.z = lastScale.z + (scale.z - lastScale.z) * partialTicks;

		return Maths.createTransformationMatrix(renderPosition, renderRotation, renderScale);
	}

	public boolean isInsideFrustum() {
		return insideFrustum;
	}

	public GLObject setPosition(Vector3f position) {
		this.position = position;
		return this;
	}

	public GLObject setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		return this;
	}

	public GLObject setLastPosition(float x, float y, float z) {
		this.lastPosition.x = x;
		this.lastPosition.y = y;
		this.lastPosition.z = z;
		return this;
	}

	public GLObject move(float x, float y, float z) {
		this.direction.x = x;
		this.direction.y = y;
		this.direction.z = z;
		return this;
	}

	public GLObject setRotation(Vector3f rotation) {
		this.rotation = rotation;
		return this;
	}

	public GLObject setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
		return this;
	}

	public GLObject setScale(Vector3f scale) {
		this.scale = scale;
		return this;
	}

	public GLObject setScale(float x, float y, float z) {
		this.scale.x = x;
		this.scale.y = y;
		this.scale.z = z;
		return this;
	}

	public void setInsideFrustum(boolean insideFrustum) {
		this.insideFrustum = insideFrustum;
	}
}