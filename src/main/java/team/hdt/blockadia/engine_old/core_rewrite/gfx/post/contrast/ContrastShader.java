package team.hdt.blockadia.engine.core_rewrite.gfx.post.contrast;

import team.hdt.blockadia.engine.core_rewrite.gfx.shader.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private int location_contrast;
	
	public ContrastShader() {
		super("blockadia", "post/contrast");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_contrast = super.getUniformLocation("contrast");
	}
	
	public void loadContrast(float contrast) {
		super.loadFloat(location_contrast, contrast);
	}
}