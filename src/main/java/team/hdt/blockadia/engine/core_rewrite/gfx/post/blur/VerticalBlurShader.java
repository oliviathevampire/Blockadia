package team.hdt.blockadia.engine.core_rewrite.gfx.post.blur;

import team.hdt.blockadia.engine.core_rewrite.gfx.shader.ShaderProgram;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

public class VerticalBlurShader extends ShaderProgram {

	private int location_targetHeight;

	protected VerticalBlurShader() {
		super(new Identifier("shaders/post/verticalblur_vert.glsl"), new Identifier("shaders/post/blur_frag.glsl"));
	}

	protected void loadTargetHeight(float height) {
		super.loadFloat(location_targetHeight, height);
	}

	@Override
	protected void getAllUniformLocations() {
		location_targetHeight = super.getUniformLocation("targetHeight");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
