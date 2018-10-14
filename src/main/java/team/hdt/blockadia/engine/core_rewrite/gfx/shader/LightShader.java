package team.hdt.blockadia.engine.core_rewrite.gfx.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.gfx.light.Light;
import team.hdt.blockadia.engine.core_rewrite.object.ICamera;
import team.hdt.blockadia.engine.core_rewrite.util.Maths;

public class LightShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_transformationMatrix;
	private int location_viewMatrix;

	private int location_lightColor;
	private int location_lightSize;

	public LightShader() {
		super("blockadia", "light");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");

		location_lightColor = super.getUniformLocation("lightColor");
		location_lightSize = super.getUniformLocation("lightSize");
	}

	public void loadLight(Light light) {
		super.loadMatrix(location_transformationMatrix, Maths.createTransformationMatrix(light.getPosition(), new Vector2f(light.getSize())));
		super.loadVector(location_lightColor, light.getColor());
		super.loadFloat(location_lightSize, light.getSize());
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(ICamera camera) {
		super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
	}
}