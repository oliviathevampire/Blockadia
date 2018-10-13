package team.hdt.blockadia.engine.core_rewrite.gfx.shader;

import org.joml.Matrix4f;

public class TileShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_numberOfRows;
	
	private int location_tileTexture;
	private int location_tileGlowTexture;

	public TileShader() {
		super("blockadia", "tile");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "textureOffset");
		super.bindAttribute(6, "textureOffset1");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		
		location_tileTexture = super.getUniformLocation("tileTexture");
		location_tileGlowTexture = super.getUniformLocation("tileGlowTexture");
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_tileTexture, 0);
		super.loadInt(location_tileGlowTexture, 1);
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
}