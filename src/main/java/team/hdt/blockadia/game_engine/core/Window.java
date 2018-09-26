package team.hdt.blockadia.game_engine.core;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import team.hdt.blockadia.game_engine.core.input.Input;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;

import java.nio.IntBuffer;

public class Window {

    private static GLFWErrorCallback errorCallback = null;
    private final GLFWWindowPosCallback posCallback;
    private final Vectors2f windowPos = new Vectors2f();
    private final GLFWWindowSizeCallback sizeCallback;
    private final Vectors2f windowSize = new Vectors2f();
    private final GLFWCursorEnterCallback cursorEnterCallback;
    private final GLFWCursorPosCallback cursorPosCallback;
    private final Vectors2f cursorPos = new Vectors2f();
    private final GLFWMouseButtonCallback mouseButtonCallback;
    private final GLFWKeyCallback keyCallback;
    public long windowID;
    public Input input;
    private Scene scene;
    private boolean cursorEnter = false;

    private Window(long id) {
        this.windowID = id;
        input = new Input();

        posCallback = new GLFWWindowPosCallback() {
            public void invoke(long window, int xpos, int ypos) {
                windowPos.set(xpos, ypos);
            }
        };
        GLFW.glfwSetWindowPosCallback(windowID, posCallback);

        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int width, int height) {
                windowSize.set(width, height);
            }
        };
        GLFW.glfwSetWindowSizeCallback(windowID, sizeCallback);

        cursorEnterCallback = new GLFWCursorEnterCallback() {
            public void invoke(long window, boolean entered) {
                cursorEnter = entered;
            }
        };
        GLFW.glfwSetCursorEnterCallback(windowID, cursorEnterCallback);

        cursorPosCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                cursorPos.set((float) xpos, (float) ypos);
            }
        };
        GLFW.glfwSetCursorPosCallback(windowID, cursorPosCallback);

        mouseButtonCallback = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    input.setKeyDown(button);
                } else if (action == GLFW.GLFW_RELEASE) {
                    input.setKeyUp(button);
                }
            }
        };
        GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback);

        keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    input.setKeyDown(key);
                } else if (action == GLFW.GLFW_RELEASE) {
                    input.setKeyUp(key);
                }
            }
        };
        GLFW.glfwSetKeyCallback(windowID, keyCallback);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowSize(windowID, width, height);
        windowSize.set(width.get(), height.get());
    }

    public static Window createWindow(Scene scene, String title) {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException();
        }

        errorCallback = new GLFWErrorCallback() {
            public void invoke(int error, long description) {
                System.err.println("[" + error + "] " + description);
            }
        };
        GLFW.glfwSetErrorCallback(errorCallback);

        long window = GLFW.glfwCreateWindow(scene.getWidth(), scene.getHeight(), title, MemoryUtil.NULL, MemoryUtil.NULL);

        if (window == MemoryUtil.NULL) {
            System.err.println("Window returned NULL");
            System.exit(-1);
        }

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        GLFW.glfwSwapInterval(1);

        scene.setGLinitilized();
        return new Window(window);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void enableDepthBuffer() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void disableDepthBuffer() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public void enableStencilBuffer() {
        GL11.glEnable(GL11.GL_STENCIL_TEST);
    }

    public void disableStencilBuffer() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    /**
     * Checks if the window has been set to close
     *
     * @return
     */
    public boolean isCloseRequested() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
        return GLFW.glfwWindowShouldClose(windowID);
    }

    public void update() {
        input.reset();
        GLFW.glfwSwapBuffers(windowID);
        GLFW.glfwPollEvents();
    }

    public void close() {
        GLFW.glfwDestroyWindow(windowID);
    }

    /**
     * checks if the cursor is on top and within bounds of the window
     *
     * @return
     */
    public boolean getCursorOnWindow() {
        return cursorEnter;
    }

    public int getXpos() {
        return (int) Math.floor(windowPos.x);
    }

    public int getYpos() {
        return (int) Math.floor(windowPos.y);
    }

    public float getMouseX() {
        return cursorPos.x;
    }

    public float getMouseY() {
        return cursorPos.y;
    }

    /**
     * returns the position of the mouse on the Window
     *
     * @return
     */
    public Vectors2f getMousePos() {
        return cursorPos;
    }

    /**
     * returns the location of the Window on your screen
     *
     * @return
     */
    public Vectors2f getPos() {
        return windowPos;
    }

    public int getWidth() {
        return (int) Math.floor(windowSize.x);
    }

    public int getHeight() {
        return (int) Math.floor(windowSize.y);
    }

    /**
     * returns the size of the Window
     *
     * @return
     */
    public Vectors2f getSize() {
        return windowSize;
    }

    public void disposeGLFW() {
        Callbacks.glfwFreeCallbacks(windowID);
        GLFW.glfwTerminate();
    }

}