package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui;

import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLProgram;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public class ProgramTexturedQuad extends GLProgram {
    private int uvs;
    private int alpha;
    private int transfMatrix;

    public ProgramTexturedQuad() {
        super();
        this.addShader(GLH.glhLoadShader(R.getResPath("shaders/gui/quadTextured.fs"), GL20.GL_FRAGMENT_SHADER));
        this.addShader(GLH.glhLoadShader(R.getResPath("shaders/gui/quadTextured.gs"), GL32.GL_GEOMETRY_SHADER));
        this.addShader(GLH.glhLoadShader(R.getResPath("shaders/gui/quadTextured.vs"), GL20.GL_VERTEX_SHADER));
        this.link();
    }

    @Override
    public void bindAttributes() {
    }

    @Override
    public void linkUniforms() {
        this.uvs = super.getUniform("uvs");
        this.transfMatrix = super.getUniform("transfMatrix");
        this.alpha = super.getUniform("alpha");
    }

    public void loadQuadTextured(GLTexture glTexture, float ux, float uy, float vx, float vy, float alpha,
                                 Matrix4f transformMatrix) {
        glTexture.bind(GL13.GL_TEXTURE0, GL11.GL_TEXTURE_2D);
        super.loadUniformMatrix(this.transfMatrix, transformMatrix);
        super.loadUniformVec(this.uvs, ux, uy, vx, vy);
        super.loadUniformFloat(this.alpha, alpha);
    }
}
