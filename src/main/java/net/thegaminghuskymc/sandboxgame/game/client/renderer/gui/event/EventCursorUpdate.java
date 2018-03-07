package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLCursor;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class EventCursorUpdate extends Event {

    private GLFWWindow glfwWindow;
    private GLCursor nextCursor;
    private GLCursor prevCursor;
    private Gui topestGui;

    public EventCursorUpdate() {
        super();
    }

    @Override
    protected void onReset() {
    }

    @Override
    protected void process() {
        this.prevCursor = this.glfwWindow.getCursor();
        this.glfwWindow.setCursor(this.nextCursor);
    }

    @Override
    protected void unprocess() {
        this.glfwWindow.setCursor(this.prevCursor);
    }

    public final Gui getTopestGui() {
        return (this.topestGui);
    }

    /**
     * return next cursor to be set
     */
    public final GLCursor getCursor() {
        return (this.nextCursor);
    }

    public final void setCursor(GLCursor glCursor) {
        this.nextCursor = glCursor;
    }

    public void reset(GLFWWindow glfwWindow, GLCursor glCursor, Gui topestGui) {
        super.reset();
        this.glfwWindow = glfwWindow;
        this.nextCursor = glCursor;
        this.topestGui = topestGui;
    }
}
