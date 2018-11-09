package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.post.blur;

import team.hdt.blockadia.old_engine_code_1.core_rewrite.util.Identifier;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.shader.ShaderProgram;

public class HorizontalBlurShader extends ShaderProgram {

	private int location_targetWidth;

	protected HorizontalBlurShader() {
		super(new Identifier("shaders/post/horizontalblur_vert.glsl"), new Identifier("shaders/post/blur_frag.glsl"));
	}

	protected void loadTargetWidth(float width) {
		super.loadFloat(location_targetWidth, width);
	}

	@Override
	protected void getAllUniformLocations() {
		location_targetWidth = super.getUniformLocation("targetWidth");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}