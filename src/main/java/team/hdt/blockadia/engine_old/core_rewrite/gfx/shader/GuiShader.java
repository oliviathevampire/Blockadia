package team.hdt.blockadia.engine_old.core_rewrite.gfx.shader;

import org.joml.Matrix4f;
import team.hdt.blockadia.engine_old.core_rewrite.gfx.gui.GuiTexture;

public class GuiShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_transformationMatrix;

	private int location_textureType;

	private int location_textureOffsets;
	private int location_textureSize;

	private int location_color;

	public GuiShader() {
		super("blockadia", "gui");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		
		location_textureType = super.getUniformLocation("textureType");

		location_textureOffsets = super.getUniformLocation("textureOffsets");
		location_textureSize = super.getUniformLocation("textureSize");
		
		location_color = super.getUniformLocation("color");
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadTexture(GuiTexture texture) {
		super.loadInt(location_textureType, texture.getType());
		if (texture.getType() == GuiTexture.TEXTURE) {
			super.loadVector(location_textureOffsets, texture.getTextureOffsets());
			super.loadFloat(location_textureSize, texture.getTextureSize());
		} else if (texture.getType() == GuiTexture.COLOR) {
			super.loadVector(location_color, texture.getColor());
		}
	}
}