package net.thegaminghuskymc.sandboxgame.engine.client.hud;

import net.thegaminghuskymc.sandboxgame.engine.Window;

public interface IHudComponent {

    void init(Window window) throws Exception;

    void render(Window window);

    void cleanup();

}
