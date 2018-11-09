package team.hdt.blockadia.old_engine_code_1.core_rewrite.object;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Quad extends GLObject {

	private Vector4f color;

	public Quad(Vector3f position, Vector3f rotation, Vector3f scale, Vector4f color) {
		this.renderPosition = new Vector3f(position);
		this.lastPosition = new Vector3f(position);
		this.position = new Vector3f(position);

		this.renderRotation = new Vector3f(rotation);
		this.lastRotation = new Vector3f(rotation);
		this.rotation = new Vector3f(rotation);

		this.renderScale = new Vector3f(scale);
		this.lastScale = new Vector3f(scale);
		this.scale = new Vector3f(scale);

		this.color = color;
	}

	public Vector4f getColor() {
		return color;
	}
	
	public void setColor(Vector4f color) {
		this.color = color;
	}

	@Override
	public boolean isInsideFrustum() {
		return true;
	}
}