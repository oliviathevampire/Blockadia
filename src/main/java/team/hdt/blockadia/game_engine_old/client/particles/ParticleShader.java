package team.hdt.blockadia.game_engine_old.client.particles;

import team.hdt.blockadia.game_engine_old.client.rendering.shaders.ShaderProgram;
import team.hdt.blockadia.game_engine_old.client.rendering.shaders.UniformFloat;
import team.hdt.blockadia.game_engine_old.client.rendering.shaders.UniformMatrix;
import team.hdt.blockadia.game_engine_old.client.rendering.shaders.UniformVec3;
import team.hdt.blockadia.game_engine_old.util.MyFile;

public class ParticleShader extends ShaderProgram {

    private static final MyFile VERTEX_SHADER = new MyFile("particles", "particleVShader.glsl");
    private static final MyFile FRAGMENT_SHADER = new MyFile("particles", "particleFShader.glsl");

    protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
    protected UniformFloat numberOfRows = new UniformFloat("numberOfRows");
    protected UniformVec3 lighting = new UniformVec3("lighting");

    public ParticleShader() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
        super.storeAllUniformLocations(projectionMatrix, numberOfRows, lighting);
    }

}
