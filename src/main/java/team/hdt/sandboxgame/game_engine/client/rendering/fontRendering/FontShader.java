package team.hdt.sandboxgame.game_engine.client.rendering.fontRendering;

import team.hdt.sandboxgame.game_engine.client.rendering.shaders.*;

public class FontShader extends ShaderProgram {
	
	private static final String VERTEX_SHADER = "src/main/resources/assets/sandboxgame/shaders/fontVertex.glsl";
	private static final String FRAGMENT_SHADER = "src/main/resources/assets/sandboxgame/shaders/fontFragment.glsl";
	
	protected UniformVec4 colour = new UniformVec4("colour");
	protected UniformSampler fontTexture = new UniformSampler("fontTexture");
	protected UniformVec3 transform = new UniformVec3("transform");
	protected UniformVec3 borderColour = new UniformVec3("borderColour");
	protected UniformVec2 borderSizes = new UniformVec2("borderSizes");
	protected UniformVec2 edgeData = new UniformVec2("edgeData");

	public FontShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		super.storeAllUniformLocations(colour, fontTexture, transform, borderColour, borderSizes, edgeData);
	}

}
