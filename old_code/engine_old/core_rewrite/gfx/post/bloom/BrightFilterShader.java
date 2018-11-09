package team.hdt.blockadia.engine.core_rewrite.gfx.post.bloom;

import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.gfx.shader.ShaderProgram;

public class BrightFilterShader extends ShaderProgram {

	public BrightFilterShader() {
		super(new Identifier("shaders/post/bloom/simple_vert.glsl"), new Identifier("shaders/post/bloom/bright_frag.glsl"));
	}

	@Override
	protected void getAllUniformLocations() {
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}