package team.hdt.sandboxgame.game_engine.client.rendering.guiRendering;

import team.hdt.sandboxgame.game_engine.client.rendering.shaders.*;
import team.hdt.sandboxgame.game_engine.util.MyFile;

public class GuiShader extends ShaderProgram {
	
	private static final MyFile VERTEX_SHADER = new MyFile("guiRendering", "guiVertex.glsl");
	private static final MyFile FRAGMENT_SHADER = new MyFile("guiRendering", "guiFragment.glsl");
	
	public UniformVec4 transform = new UniformVec4("transform");
	public UniformVec3 overrideColour = new UniformVec3("overrideColour");
	public UniformBoolean useOverrideColour = new UniformBoolean("useOverrideColour");
	public UniformFloat alpha = new UniformFloat("alpha");
	public UniformBoolean flipTexture = new UniformBoolean("flipTexture");
	public UniformBoolean usesBlur = new UniformBoolean("usesBlur");
	public UniformSampler blurTexture = new UniformSampler("blurTexture");
	public UniformSampler guiTexture = new UniformSampler("guiTexture");

	public GuiShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		super.storeAllUniformLocations(transform, alpha, flipTexture, overrideColour, useOverrideColour, blurTexture, guiTexture, usesBlur);
		super.start();
		guiTexture.loadTexUnit(0);
		blurTexture.loadTexUnit(1);
		super.stop();
	}
	
	

}
