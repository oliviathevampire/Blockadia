package team.hdt.blockadia.game_engine.client.rendering.guiRendering;

import team.hdt.blockadia.game_engine.client.rendering.shaders.*;
import team.hdt.blockadia.game_engine.util.MyFile;

public class GuiShader extends ShaderProgram {

    private static final MyFile VERTEX_SHADER = new MyFile("src/main/resources/assets/blockania/shaders/");
    private static final MyFile FRAGMENT_SHADER = new MyFile("src/main/resources/assets/blockania/shaders/");

    public UniformVec4 transform = new UniformVec4("transform");
    public UniformVec3 overrideColour = new UniformVec3("overrideColour");
    public UniformBoolean useOverrideColour = new UniformBoolean("useOverrideColour");
    public UniformFloat alpha = new UniformFloat("alpha");
    public UniformBoolean flipTexture = new UniformBoolean("flipTexture");
    public UniformBoolean usesBlur = new UniformBoolean("usesBlur");
    public UniformSampler blurTexture = new UniformSampler("blurTexture");
    public UniformSampler guiTexture = new UniformSampler("guiTexture");

    public GuiShader() {
        super(VERTEX_SHADER + "guiVertex.glsl", FRAGMENT_SHADER + "guiFragment.glsl");
        super.storeAllUniformLocations(transform, alpha, flipTexture, overrideColour, useOverrideColour, blurTexture, guiTexture, usesBlur);
        super.start();
        guiTexture.loadTexUnit(0);
        blurTexture.loadTexUnit(1);
        super.stop();
    }

}