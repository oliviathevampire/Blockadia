package team.hdt.sandboxgame.game_engine.common.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import team.hdt.sandboxgame.game_engine.client.rendering.GLShapes;
import team.hdt.sandboxgame.game_engine.client.rendering.TextureLoader;
import team.hdt.sandboxgame.game_engine.common.world.Arena;
import team.hdt.sandboxgame.game_engine.common.world.block.Block;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

    private static State gameState = State.IN_GAME;

    private static int width;
    private static int height;
    public long window;
    private Arena arena;
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
        if(gameState == State.IN_GAME) {
            arena = new Arena();
            arena.genDemoBlocks();
            TextureLoader.loadTextures(false);
            TextureLoader.bind(TextureLoader.Textures.SHEET);
            GLShapes.drawCube(Block.BlockType.GRASS, 0, 0, 0);
        }
        if(gameState == State.MAIN_MENU) {
            TextureLoader.loadTextures(false);
            TextureLoader.bind(TextureLoader.Textures.MAIN_MENU_LOGO);
        }
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            if(gameState == State.IN_GAME) {
                glShadeModel(GL_SMOOTH);
                glClearColor(0.53f, 0.8f, 0.98f, 0.0f); // Sky blue
                glClearDepth(1.0);
                glEnable(GL_DEPTH_TEST);
                glDepthFunc(GL_LEQUAL);
                // Transparency
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_ALPHA_TEST);
                glAlphaFunc(GL_GREATER, 0.0f);
                glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
                arena.render();
            }
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public void run() {
        loop();
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

    private enum State {
        MAIN_MENU, IN_GAME, PAUSE_MENU, SETTINGS
    }

}
