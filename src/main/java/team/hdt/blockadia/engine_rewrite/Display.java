package team.hdt.blockadia.engine_rewrite;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import java.nio.DoubleBuffer;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

	private static String title;
	private static int width;
	private static int height;
	private static boolean fullscreen;

	private static DoubleBuffer mouseXBuffer = BufferUtils.createDoubleBuffer(1);
	private static DoubleBuffer mouseYBuffer = BufferUtils.createDoubleBuffer(1);

	private static long windowID;
	private static long cursorID;

    public Display() {
    }

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
	}

	public static void createDisplay(String title, int width, int height) {
		createDisplay(title, width, height, false);
	}

	private static void createDisplay(String title, int width, int height, boolean borderless) {
		Display.title = title;
		Display.width = width;
		Display.height = height;
		Display.fullscreen = borderless;

		if (!GLFW.glfwInit()) {
			throw new RuntimeException("Failed to initialize GLFW");
		}

		if (isCreated())
			throw new RuntimeException("Display was already created!");
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, borderless ? GLFW.GLFW_FALSE : GLFW.GLFW_TRUE);

		windowID = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);

		if (windowID == NULL) {
			throw new RuntimeException("Could not create window");
		}

		GLFW.glfwShowWindow(windowID);
		GLFW.glfwFocusWindow(windowID);

		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(windowID, (int) ((vidMode.width() * 0.5) - (width * 0.5)), (int) ((vidMode.height() * 0.5) - (height * 0.5)));

		GLFW.glfwMakeContextCurrent(windowID);
		GL.createCapabilities();
	}

	public static void update() {
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(windowID);
	}

	public static void destroy() {
		if (cursorID != NULL) {
			GLFW.glfwDestroyCursor(cursorID);
			cursorID = NULL;
		}
		GLFW.glfwDestroyWindow(windowID);
		windowID = NULL;
		GLFW.glfwTerminate();
	}

	public static void close() {
		GLFW.glfwSetWindowShouldClose(windowID, true);
	}

	public static void setFullscreen() {
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		destroy();
		createDisplay(title, vidMode.width(), vidMode.height(), true);
	}

	public static void setWindowed() {
		destroy();
		createDisplay(title, width, height);
	}

	public static String getTitle() {
		return title;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static boolean isFullscreen() {
		return fullscreen;
	}

	public static double getMouseX() {
		GLFW.glfwGetCursorPos(windowID, mouseXBuffer, mouseYBuffer);
		return mouseXBuffer.get(0);
	}

	public static double getMouseY() {
		GLFW.glfwGetCursorPos(windowID, mouseXBuffer, mouseYBuffer);
		return mouseYBuffer.get(0);
	}

	public static int getMouseButton() {
		return GLFW.glfwGetMouseButton(windowID, 0) == GLFW.GLFW_TRUE ? 0 : GLFW.glfwGetMouseButton(windowID, 1) == GLFW.GLFW_TRUE ? 1 : GLFW.glfwGetMouseButton(windowID, 2) == GLFW.GLFW_TRUE ? 2 : -1;
	}

	public static boolean isCreated() {
		return windowID != NULL;
	}

	public static boolean isCloseRequested() {
		return GLFW.glfwWindowShouldClose(windowID);
	}

	public static boolean isKeyPressed(int key) {
		return GLFW.glfwGetKey(windowID, key) == GLFW.GLFW_PRESS;
	}

	public static void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowID, title);
		Display.title = title;
	}

	public static void setSize(int width, int height) {
		GLFW.glfwSetWindowSize(windowID, width, height);
		Display.width = width;
		Display.height = height;
	}
}