package team.hdt.blockadia.game_engine.core;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import team.hdt.blockadia.game_engine.client.BlockadiaClient;
import team.hdt.blockadia.game_engine.client.rendering.MainRenderer;
import team.hdt.blockadia.game_engine.client.rendering.shader.StaticShader;
import team.hdt.blockadia.game_engine.core.entity.BaseEntity;
import team.hdt.blockadia.game_engine.core.util.GameSide;
import team.hdt.blockadia.game_engine.core.util.GameSideOnly;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@GameSideOnly(GameSide.CORE)
public class Display {

    public static int width;
    public static int height;
    public long window;
    DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
    DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);

    public Display(String title, int windowWidth, int windowHeight) {
        width = windowWidth;
        height = windowHeight;
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(windowWidth, windowHeight, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });
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
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    private void loop() {
        GL.createCapabilities();
        //BlockadiaClient.gameRender();
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);
            glfwPollEvents();
            StaticShader shader = new StaticShader();
            MainRenderer renderer = new MainRenderer(shader);
            for (BaseEntity entity : BlockadiaClient.entities) {
                renderer.render(entity, shader);
            }
        }
    }

    public void run() {
        loop();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void TestEnd() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public double getMouseX() {
        glfwGetCursorPos(window, xBuffer, yBuffer);
        return xBuffer.get(0);
    }

    public double getMouseY() {
        glfwGetCursorPos(window, xBuffer, yBuffer);
        return yBuffer.get(0);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

}
