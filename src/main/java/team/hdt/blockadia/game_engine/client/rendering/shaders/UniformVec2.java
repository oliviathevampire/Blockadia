package team.hdt.blockadia.game_engine.client.rendering.shaders;

import org.lwjgl.opengl.GL20;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;

public class UniformVec2 extends Uniform {

	private float currentX;
	private float currentY;
	private boolean used = false;

	public UniformVec2(String name) {
		super(name);
	}

	public void loadVec2(Vectors2f vector) {
		loadVec2(vector.x, vector.y);
	}

	public void loadVec2(float x, float y) {
		if (!used || x != currentX || y != currentY) {
			this.currentX = x;
			this.currentY = y;
			used = true;
			GL20.glUniform2f(super.getLocation(), x, y);
		}
	}

}
