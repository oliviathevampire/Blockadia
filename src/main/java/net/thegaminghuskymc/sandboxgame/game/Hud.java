package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.Window;
import net.thegaminghuskymc.sandboxgame.engine.client.hud.HudTopBar;
import net.thegaminghuskymc.sandboxgame.engine.gui_elements.Button;
import net.thegaminghuskymc.sandboxgame.engine.gui_elements.Font;
import org.joml.Vector2d;

public class Hud {

    private HudTopBar hud = new HudTopBar();
    private Button but;
    private Font font;

    public void init(Window window) throws Exception {
        hud.init(window);
//        font = new Font(window);
//        font.loadFont("roboto");
//        font.test();
        but = new Button(window, window.getWidth()/2, window.getHeight()/2, 400, 50, "MGlolenstine", 0);
    }

    public void render(Window window) {
        hud.render(window);
//        font.showText(new Vector2d(0, window.getHeight()/2));
        but.show();
    }

    public void cleanup() {
        hud.cleanup();
    }

}