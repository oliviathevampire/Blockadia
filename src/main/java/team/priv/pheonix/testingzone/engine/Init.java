/*
 * this is to only test for things for the game
 * this is a warning from pheonix
 * if works pheonixfirewingz will add to the main game
 * -------------------------------------
 * @author Pheonixfirewingz aka Mystic4pheonix.
 * -------------------------------------
 */
package team.priv.pheonix.testingzone.engine;

import org.lwjgl.opengl.GL;
import team.priv.pheonix.testingzone.utils.Mesh;
import team.priv.pheonix.testingzone.utils.Renderer;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

/**
 * @author 326296
 */
public class Init {
    public static boolean run = true;
    public static Renderer renderer = new Renderer();

    public static void run() {
        GL.createCapabilities();
        init();
        float[] positions = new float[]{
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,};
        int[] indices = new int[]{0, 1, 3, 3, 1, 2};
        Mesh mesh = new Mesh(positions, indices);
        while (run) {
            glfwSwapInterval(1);
            glfwSwapBuffers(TestMain.display.window);
            renderer.render(mesh);
        }
        mesh.cleanUp();
    }

    public static void init() {
        try {
            renderer.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
