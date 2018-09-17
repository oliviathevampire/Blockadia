/*
 * this is to only test for things for the game
 * this is a warning from pheonix
 * if works pheonixfirewingz will add to the main game
 * -------------------------------------
 * @author Pheonixfirewingz aka Mystic4pheonix.
 * -------------------------------------
 */
package team.priv.pheonix.testingzone.engine;

import team.priv.pheonix.testingzone.utils.Mesh;
import team.priv.pheonix.testingzone.utils.Renderer;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

/**
 *
 * @author 326296
 */
public class Init {
    public static boolean run = true;
    public static Renderer renderer = new Renderer();


    public void init() throws Exception {
        try {
            renderer.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void run() {
        while (run = true) {
            glfwSwapInterval(1);
            glfwSwapBuffers(TestMain.display.window);
            float[] positions = new float[]{
                    -0.5f, 0.5f, 0.0f,
                    -0.5f, -0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f,
                    0.5f, 0.5f, 0.0f,};
            int[] indices = new int[]{
                    0, 1, 3, 3, 1, 2,};
            Mesh mesh = new Mesh(positions, indices);
            renderer.render(TestMain.display.window, mesh);
        }
    }

    
}
