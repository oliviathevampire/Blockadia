package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.Window;
import net.thegaminghuskymc.sandboxgame.engine.client.hud.HudTopBar;

public class Hud {

    private HudTopBar hud = new HudTopBar();

    public void init(Window window) throws Exception {
        hud.init(window);
    }

    public void render(Window window) {
        hud.render(window);
    }

    public void cleanup() {
        hud.cleanup();
    }

}