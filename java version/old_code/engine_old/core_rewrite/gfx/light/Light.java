package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.light;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class Light {

	private Vector2f position;
	private Vector4f color;
	private float size;

	public Light(Vector2f position, Vector4f color, float size) {
		this.position = position;
		this.color = color;
		this.size = size;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector4f getColor() {
		return color;
	}

	public float getSize() {
		return size;
	}
}