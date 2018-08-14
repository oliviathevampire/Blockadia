package team.hdt.blockadia.game_engine.client.rendering.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Matrix4fs;

import java.nio.FloatBuffer;

public class UniformMatrix extends Uniform {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public UniformMatrix(String name) {
        super(name);
    }

    public void loadMatrix(Matrix4fs matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4fv(super.getLocation(), false, matrixBuffer);
    }

}