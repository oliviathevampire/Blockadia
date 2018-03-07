/**
 * *	This file is part of the project https://github.com/toss-dev/VoxelEngine
 * *
 * *	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
 * *
 * *	PEREIRA Romain
 * *                                       4-----7
 * *                                      /|    /|
 * *                                     0-----3 |
 * *                                     | 5___|_6
 * *                                     |/    | /
 * *                                     1-----2
 */

package net.thegaminghuskymc.sandboxgame.game.client.renderer.model;

import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLProgram;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class ProgramModel extends GLProgram {

    public static final int MAX_JOINTS = 32;

    private int mvpMatrix;
    private int transfMatrix;
    private int[] jointTransforms;

    private int skinTexture;

    public ProgramModel() {
        super();
        this.addShader(GLH.glhLoadShader(R.getResPath("shaders/model.fs"), GL20.GL_FRAGMENT_SHADER));
        this.addShader(GLH.glhLoadShader(R.getResPath("shaders/model.vs"), GL20.GL_VERTEX_SHADER));
        this.link();
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uv");
        super.bindAttribute(2, "normal");
        super.bindAttribute(3, "jointIDS");
        super.bindAttribute(4, "jointWeights");
    }

    @Override
    public void linkUniforms() {
        this.mvpMatrix = super.getUniform("mvp_matrix");
        this.transfMatrix = super.getUniform("transf_matrix");

        this.jointTransforms = new int[MAX_JOINTS];
        for (int i = 0; i < MAX_JOINTS; i++) {
            this.jointTransforms[i] = super.getUniform("jointTransforms[" + i + "]");
        }

        this.skinTexture = super.getUniform("skinTexture");
    }

    public void loadCamera(CameraProjective camera) {
        this.loadUniformMatrix(this.mvpMatrix, camera.getMVPMatrix());
    }

    public void loadModel(Model model) {

    }

    public void loadModelInstance(ModelInstance modelInstance) {
        // joint matrices
        Matrix4f[] jointTransformMatrices = modelInstance.getSkeleton().getBoneTransforms();
        for (int i = 0; i < jointTransformMatrices.length; i++) {
            super.loadUniformMatrix(this.jointTransforms[i], jointTransformMatrices[i]);
        }

        // transformation matrix
        Matrix4f transf = new Matrix4f();
        WorldEntity entity = modelInstance.getEntity();
        transf.translate(entity.getPositionX(), entity.getPositionY(), entity.getPositionZ());
        transf.translate(entity.getSizeX() * 0.5f, entity.getSizeY() * 0.5f, entity.getSizeZ() * 0.5f);
        transf.rotateXYZ((float) Math.toRadians(entity.getRotationX()), (float) Math.toRadians(entity.getRotationY()),
                (float) Math.toRadians(entity.getRotationZ()));
        transf.translate(-entity.getSizeX() * 0.5f, -entity.getSizeY() * 0.5f, -entity.getSizeZ() * 0.5f);

        // transf.scale(64.0f);
        this.loadUniformMatrix(this.transfMatrix, transf);

        // the skin
        ModelSkin skin = modelInstance.getModel().getSkin(modelInstance.getSkinID());
        if (skin == null) {
            // Logger.get().log(Logger.Level.DEBUG, "Tried to load a NULL model
            // skin: " + modelInstance.getSkinID() + "/"
            // + modelInstance.getModel().getSkins().size());
            return;
        }
        skin.bind(GL13.GL_TEXTURE1, GL11.GL_TEXTURE_2D);
        this.loadUniformInteger(this.skinTexture, 1);
    }
}
