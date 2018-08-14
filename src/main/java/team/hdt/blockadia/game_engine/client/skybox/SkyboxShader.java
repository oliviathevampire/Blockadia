package team.hdt.blockadia.game_engine.client.skybox;

import team.hdt.blockadia.game_engine.client.rendering.shaders.*;

public class SkyboxShader extends ShaderProgram {

    private static final String VERTEX_SHADER = "src/main/java/team/hdt/blockadia/game_engine/client/skybox/skyVertex.glsl";
    private static final String FRAGMENT_SHADER = "src/main/java/team/hdt/blockadia/game_engine/client/skybox/skyFragment.glsl";

    protected UniformMatrix pvMatrix = new UniformMatrix("pvMatrix");
    protected UniformVec3 horizonColour = new UniformVec3("horizonColour");
    protected UniformVec3 skyColour = new UniformVec3("skyColour");
    protected UniformFloat skyboxSize = new UniformFloat("skyboxSize");
    protected UniformFloat starBrightness = new UniformFloat("starBrightness");
    protected UniformFloat scroll = new UniformFloat("scroll");
    protected UniformFloat time = new UniformFloat("time");
    protected UniformSampler nightSky = new UniformSampler("nightSky");
    protected UniformFloat segCount = new UniformFloat("segCount");

    public SkyboxShader() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
        super.storeAllUniformLocations(pvMatrix, horizonColour, skyboxSize, skyColour, starBrightness, nightSky, scroll, time, segCount);
    }

}
