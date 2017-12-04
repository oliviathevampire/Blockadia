package net.thegaminghuskymc.sandboxgame.game.client.renderer;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;

public abstract class EventRender extends Event {
    private final MainRenderer renderer;

    public EventRender(MainRenderer renderer) {
        this.renderer = renderer;
    }

    public MainRenderer getRenderer() {
        return (this.renderer);
    }

    public GLFWWindow getGLFWWindow() {
        return (this.renderer.getGLFWWindow());
    }

}
