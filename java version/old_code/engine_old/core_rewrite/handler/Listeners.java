package team.hdt.blockadia.engine.core_rewrite.handler;

import org.lwjgl.glfw.*;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;
import team.hdt.blockadia.engine.core_rewrite.Display;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * This should not be used by modders. Ever.
 * 
 * @author Ocelot5836
 */
public class Listeners {

	public static final GLFWKeyCallback KEY_CALLBACK = new KeyCallback();
	public static final GLFWMouseButtonCallback MOUSE_CALLBACK = new MouseCallback();
	public static final GLFWScrollCallback SCROLL_CALLBACK = new ScrollCallback();
	public static final GLFWJoystickCallback JOYSTICK_CALLBACK = new JoystickCallback();

	private static class KeyCallback extends GLFWKeyCallback {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (action == GLFW.GLFW_PRESS) {
				Blockadia.getInstance().onKeyPressed(key);
			}

			if (action == GLFW.GLFW_RELEASE) {
				Blockadia.getInstance().onKeyReleased(key);
			}
		}
	}

	private static class MouseCallback extends GLFWMouseButtonCallback {
		@Override
		public void invoke(long window, int button, int action, int mods) {
			if (action == GLFW.GLFW_PRESS) {
				Blockadia.getInstance().onMousePressed(Display.getMouseX(), Display.getMouseY(), Display.getMouseButton());
			}

			if (action == GLFW.GLFW_RELEASE) {
				Blockadia.getInstance().onMouseReleased(Display.getMouseX(), Display.getMouseY(), Display.getMouseButton());
			}
		}
	}

	private static class ScrollCallback extends GLFWScrollCallback {
		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			Blockadia.getInstance().onMouseScrolled(Display.getMouseX(), Display.getMouseY(), yoffset);
		}
	}

	private static class JoystickCallback extends GLFWJoystickCallback {
		@Override
		public void invoke(int jid, int event) {
			if (jid == GLFW.GLFW_JOYSTICK_1) {
				Display.joystickPresent = GLFW.glfwJoystickPresent(jid);
				if (Display.joystickPresent) {
					Blockadia.logger().info("Joystick 1 was detected");
				} else {
					Blockadia.logger().info("No joystick was detected");
				}
			}
		}
	}
}