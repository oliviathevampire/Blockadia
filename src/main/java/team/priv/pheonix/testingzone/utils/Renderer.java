/*
 * this is to only test for things for the game
 * this is a warning from pheonix
 * if works pheonixfirewingz will add to the main game
 * -------------------------------------
 * @author Pheonixfirewingz aka Mystic4pheonix.
 * -------------------------------------
 */
package team.priv.pheonix.testingzone.utils;

import team.hdt.blockadia.game_engine.core.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {

    private ShaderProgram shaderProgram;

    public Renderer() {
    }

    public void init() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Util.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Util.loadResource("/fragment.fs"));
        shaderProgram.link();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Mesh mesh) {
        clear();
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

        shaderProgram.bind();

        // Draw the mesh
        glBindVertexArray(mesh.getVaoId());
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}