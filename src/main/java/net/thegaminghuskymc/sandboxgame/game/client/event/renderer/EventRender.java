package net.thegaminghuskymc.sandboxgame.game.client.event.renderer;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;

public abstract class EventRender extends Event {
    private final MainRenderer renderer;

    public EventRender(MainRenderer renderer) {
        super();
        this.renderer = renderer;
    }

    public MainRenderer getRenderer() {
        return (this.renderer);
    }

    public GLFWWindow getGLFWWindow() {
        return (this.renderer.getGLFWWindow());
    }

}
