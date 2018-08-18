package team.hdt.blockadia.game_engine_old.client.particles;

import team.hdt.blockadia.game_engine_old.client.rendering.shaders.ShaderProgram;
import team.hdt.blockadia.game_engine_old.client.rendering.shaders.UniformMatrix;
import team.hdt.blockadia.game_engine_old.client.rendering.shaders.UniformVec3;
import team.hdt.blockadia.game_engine_old.util.MyFile;

public class ParticleColourShader extends ShaderProgram {

    private static final MyFile VERTEX_SHADER = new MyFile("particles", "particleColourVShader.glsl");
    private static final MyFile FRAGMENT_SHADER = new MyFile("particles", "particleColourFShader.glsl");

    protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
    protected UniformVec3 lighting = new UniformVec3("lighting");

    public ParticleColourShader() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
        super.storeAllUniformLocations(projectionMatrix, lighting);
    }

}
