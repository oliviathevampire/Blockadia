package team.hdt.blockadia.test.game;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.test.engine.IHud;
import team.hdt.blockadia.test.engine.Window;
import team.hdt.blockadia.test.engine.graph.FontTexture;
import team.hdt.blockadia.test.engine.item.GameItem;
import team.hdt.blockadia.test.engine.item.TextItem;

import java.awt.*;

public class Hud implements IHud {

    private static final Font FONT = new Font("Arial", Font.PLAIN, 20);

    private static final String CHARSET = "ISO-8859-1";

    private final GameItem[] gameItems;

    private final TextItem statusTextItem;

    public Hud(String statusText) throws Exception {
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        this.statusTextItem = new TextItem(statusText, fontTexture);
        this.statusTextItem.getMesh().getMaterial().setAmbientColour(new Vector4f(0.5f, 0.5f, 0.5f, 10f));

        GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        GL11.glDrawPixels(Main.WIDTH, 30, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 30);

        // Create list that holds the items that compose the HUD
        gameItems = new GameItem[]{statusTextItem};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
    }

}