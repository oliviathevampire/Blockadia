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

package net.thegaminghuskymc.sandboxgame.game.client.renderer.sky;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.engine.world.Sky;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLVertexArray;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLVertexBuffer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.Renderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.geometry.GLGeometry;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.geometry.Sphere;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class SkyRenderer extends Renderer {

    private static final int SKYDOME_PRECISION = 3;
    private static final float SKYDOME_SIZE = 1.0f;

    /** program */
    private ProgramSky programSky;

    /** vao for icosphere */
    private GLVertexArray vao;
    private GLVertexBuffer vbo;

    public SkyRenderer(MainRenderer main_renderer) {
        super(main_renderer);
    }

    @Override
    public void initialize() {
        this.programSky = new ProgramSky();

        this.vao = GLH.glhGenVAO();
        this.vbo = GLH.glhGenVBO();

        this.vao.bind();
        this.vbo.bind(GL15.GL_ARRAY_BUFFER);
        ByteBuffer floats = GLGeometry.generateSphere(SKYDOME_PRECISION, SKYDOME_SIZE);
        this.vbo.bufferData(GL15.GL_ARRAY_BUFFER, floats, GL15.GL_STATIC_DRAW);
        this.vao.setAttribute(0, 3, GL11.GL_FLOAT, false, 4 * 3, 0);
        this.vao.enableAttribute(0);

    }

    @Override
    public void deinitialize() {

        GLH.glhDeleteObject(this.programSky);
        this.programSky = null;

        GLH.glhDeleteObject(this.vao);
        this.vao = null;

        GLH.glhDeleteObject(this.vbo);
        this.vbo = null;
    }

    public void render(CameraProjective camera, Sky sky) {

        // GL11.glEnable(GL11.GL_CULL_FACE);
        // GL11.glCullFace(GL11.GL_BACK);

        this.programSky.useStart();
        this.programSky.loadUniforms(sky, camera);

        this.vao.bind();
        this.vao.draw(GL11.GL_TRIANGLES, 0, Sphere.getVertexCount(SKYDOME_PRECISION));

        this.programSky.useStop();

        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    @Override
    public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {
    }
}