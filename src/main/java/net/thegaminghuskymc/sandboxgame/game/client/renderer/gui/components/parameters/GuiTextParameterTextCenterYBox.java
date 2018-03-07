package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiText;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.font.FontModel;

/**
 * a parameter which recalculate the box dimensions automatically when changing
 * the text or the font size
 */
public class GuiTextParameterTextCenterYBox extends GuiParameter<GuiText> {
    private final float offset;

    public GuiTextParameterTextCenterYBox() {
        this(0.0f);
    }

    public GuiTextParameterTextCenterYBox(float offset) {
        super();
        this.offset = offset;
    }

    @Override
    public void run(GuiText guiText) {
        FontModel fontModel = guiText.getFontModel();
        float y = 2.0f * guiText.getBoxCenterY() - guiText.getTextHeight() - 1.0f + offset;
        fontModel.setPosition(fontModel.getX(), y, 1.0f);
    }
}