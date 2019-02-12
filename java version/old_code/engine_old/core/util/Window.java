package team.hdt.blockadia.old_engine_code_1.core.util;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public static int width = 800;
    public static int height = 800;
    public static float one_pixel_size = 2.0f / Math.min(width, height);
    public static boolean vsync = false;

    public static long window;
    public static boolean was_resize = false;
    private static double last_time;
    private static int fps;
    private static GLFWKeyCallback keyCallback;

    public static void init() {
        Window.last_time = glfwGetTime();
        Window.fps = 0;
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        }
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);

        window = glfwCreateWindow(Window.width, Window.height, "Voxel Moxel", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (Objects.requireNonNull(vidMode).width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(Window.vsync ? 1 : 0);
        glfwShowWindow(window);

        Input.setCursorDisabled(true);

        GL.createCapabilities();

        glClearColor(1f, 1f, 1f, 1.0f);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        glViewport(0, 0, Math.max(Window.width, Window.height), Math.max(Window.width, Window.height));

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {

            }
        });

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                if (Window.width != width || Window.height != height) {
                    Window.width = width;
                    Window.height = height;
                    Window.was_resize = true;
                }
            }
        });
    }

    public static void destroy() {
        glfwDestroyWindow(window);
        /*if (keyCallback != null) {
            keyCallback.release();
        }*/
    }

    public static void terminate() {
        glfwTerminate();
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public static void beforeRender() {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    }

    public static void afterRender() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public static void setupAspectRatio() {
        int size = Math.max(Window.width, Window.height);
        glViewport(0, 0, size, size);
    }

    public static boolean setTitle() {
        double current_time = glfwGetTime();
        fps++;
        if (current_time - last_time >= 1.0) {
            glfwSetWindowTitle(window,
                    "Blockadia | " +
                            fps + " fps; "/* +
                            World.chunks_in_frame + "/" + World.volume + " chunks; " +
                            World.faces_in_frame + " triangles"*/
            );
            fps = 0;
            last_time++;
            return true;
        }
        return false;
    }
}