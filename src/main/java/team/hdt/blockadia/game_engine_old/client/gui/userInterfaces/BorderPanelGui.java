package team.hdt.blockadia.game_engine_old.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine_old.client.gui.mainGuis.GuiRepository;
import team.hdt.blockadia.game_engine_old.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine_old.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine_old.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine_old.util.toolbox.Colour;

public class BorderPanelGui extends GuiComponent {

    private int pixels;

    private GuiTexture left, right, top, bottom;

    public BorderPanelGui(int pixels, Colour colour) {
        this.pixels = pixels;
        this.left = new GuiTexture(GuiRepository.BLOCK);
        left.setOverrideColour(colour);
        this.right = new GuiTexture(GuiRepository.BLOCK);
        right.setOverrideColour(colour);
        this.top = new GuiTexture(GuiRepository.BLOCK);
        top.setOverrideColour(colour);
        this.bottom = new GuiTexture(GuiRepository.BLOCK);
        bottom.setOverrideColour(colour);

    }

    @Override
    protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
        float pixelHeight = (float) pixels / Display.getHeight();
        float pixelWidth = (float) pixels / Display.getWidth();
        left.setPosition(position.x, position.y, pixelWidth, scale.y);
        top.setPosition(position.x, position.y, scale.x, pixelHeight);
        right.setPosition(position.x + scale.x - pixelWidth, position.y, pixelWidth, scale.y);
        bottom.setPosition(position.x, position.y + scale.y - pixelHeight, scale.x, pixelHeight);
    }

    @Override
    protected void updateSelf() {

    }

    @Override
    protected void getGuiTextures(GuiRenderData data) {
        data.addTexture(getLevel(), left);
        data.addTexture(getLevel(), right);
        data.addTexture(getLevel(), top);
        data.addTexture(getLevel(), bottom);
    }

}
