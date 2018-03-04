package net.thegaminghuskymc.sandboxgame.game.client.opengl.window.event;

import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;

public abstract class GLFWEvent {

	private final GLFWWindow window;

	public GLFWEvent(GLFWWindow window) {
		this.window = window;
	}

	public final GLFWWindow getGLFWWindow() {
		return (this.window);
	}

	public String getName() {
		return (this.getClass().getSimpleName());
	}
}
