package core.utils;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;



public class Keyboard {

    public static int KEY_A = GLFW.GLFW_KEY_A;
    public static int KEY_D = GLFW.GLFW_KEY_D;
    public static int KEY_S = GLFW.GLFW_KEY_S;
    public static int KEY_W = GLFW.GLFW_KEY_W;
    public static int KEY_SPACE = GLFW.GLFW_KEY_SPACE;

    public static boolean isKeyDown(long window, int key){
        if(GLFW.glfwGetKey(window,key) == GLFW_PRESS){
            return true;
        }
        else
        {
            return false;
        }
    }
}
