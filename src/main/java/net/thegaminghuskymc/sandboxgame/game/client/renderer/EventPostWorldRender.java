package net.thegaminghuskymc.sandboxgame.game.client.renderer;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.WorldRenderer;

public class EventPostWorldRender extends EventWorldRender {
    public EventPostWorldRender(WorldRenderer renderer) {
        super(renderer);
    }

    @Override
    public String getName() {
        return ("Post World Render");
    }
}
