package net.thegaminghuskymc.sandboxgame.game.client.renderer;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.WorldRenderer;

public class EventPreWorldRender extends EventWorldRender {
    public EventPreWorldRender(WorldRenderer renderer) {
        super(renderer);
    }

    @Override
    public String getName() {
        return ("Pre World Render");
    }
}
