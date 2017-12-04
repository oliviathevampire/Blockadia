package net.thegaminghuskymc.sandboxgame.game.client.renderer;

public class EventPostRender extends EventRender {
    public EventPostRender(MainRenderer renderer) {
        super(renderer);
    }

    @Override
    public String getName() {
        return ("Post World Render");
    }
}
