package net.thegaminghuskymc.sandboxgame.game.client.event.renderer;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;

public class EventPreRender extends EventRender {
    public EventPreRender(MainRenderer renderer) {
        super(renderer);
    }

    @Override
    public String getName() {
        return ("Pre Render");
    }

    @Override
    protected void process() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void unprocess() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onReset() {
        // TODO Auto-generated method stub

    }
}
