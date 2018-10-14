package team.hdt.blockadia.engine.core_rewrite.gfx.post.bloom;

import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.gfx.shader.ShaderProgram;

public class CombineShader extends ShaderProgram {

	private int location_colourTexture;
	private int location_highlightTexture;

	protected CombineShader() {
		super(new Identifier("shaders/post/bloom/simple_vert.glsl"), new Identifier("shaders/post/bloom/combine_frag.glsl"));
	}

	@Override
	protected void getAllUniformLocations() {
		location_colourTexture = super.getUniformLocation("colourTexture");
		location_highlightTexture = super.getUniformLocation("highlightTexture");
	}

	protected void connectTextureUnits() {
		super.loadInt(location_colourTexture, 0);
		super.loadInt(location_highlightTexture, 1);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}