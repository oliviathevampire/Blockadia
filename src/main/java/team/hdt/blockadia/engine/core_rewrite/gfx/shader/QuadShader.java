package team.hdt.blockadia.engine.core_rewrite.gfx.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class QuadShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_transformationMatrix;

	private int location_color;

	public QuadShader() {
		super("blockadia", "quad");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");

		location_color = super.getUniformLocation("color");
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadColor(Vector4f color) {
		super.loadVector(location_color, color);
	}
}