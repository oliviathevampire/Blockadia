package team.hdt.blockadia.engine_rewrite.client;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.engine_rewrite.MainGameLoop;

import static org.lwjgl.glfw.GLFW.*;

public class Display{
	private static String title;
	private static int width;
	private static int height;
	private static boolean fullscreen;
	private static boolean borderless;

	private static final int fps_cap = 120;
	
	private static long lastFrameTime;
	private static float delta;


	public static long window;

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

		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

		if (window == 0) {
			throw new RuntimeException("Could not create window");
		}

		GLFW.glfwShowWindow(window);
		GLFW.glfwFocusWindow(window);

		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (int) ((vidMode.width() * 0.5) - (width * 0.5)), (int) ((vidMode.height() * 0.5) - (height * 0.5)));

		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glViewport(0,0, width, height);
		lastFrameTime = getCurrentTime();
	}

	public static void close() {
		MainGameLoop.running = false;
		GLFW.glfwSetWindowShouldClose(window, true);
	}

	public static void updateDisplay(){
		glfwPollEvents();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
		GL11.glViewport(0,0, width, height);
		glfwSwapBuffers(window);
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay(){
		glfwDestroyWindow(window);
	}
	
	private static long getCurrentTime() {
		return System.nanoTime() / 100000;
	}


	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static boolean isCreated() {
		return window != 0;
	}


	public static boolean isFullscreen() {
		return fullscreen;
	}
}
