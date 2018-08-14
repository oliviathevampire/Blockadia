package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ConstantDriver;

public class IconButtonUi extends GuiClickable {

    private GuiTexture iconTexture;

    public IconButtonUi(Texture icon) {
        super(1);
        this.iconTexture = new GuiTexture(icon);
        addListener(ColourPalette.WHITE, ColourPalette.GREEN);
    }

    public IconButtonUi(Texture icon, final Colour original, final Colour moused) {
        super(1);
        this.iconTexture = new GuiTexture(icon);
        iconTexture.setOverrideColour(original);
        addListener(original, moused);
    }

    public void setAlpha(float alpha) {
        iconTexture.setAlphaDriver(new ConstantDriver(alpha));
    }


    @Override
    protected void updateSelf() {
        super.updateSelf();
        iconTexture.update();
    }

    @Override
    protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
        super.updateGuiTexturePositions(position, scale);
        iconTexture.setPosition(position.x, position.y, scale.x, scale.y);
    }

    @Override
    protected void getGuiTextures(GuiRenderData data) {
        data.addTexture(getLevel(), iconTexture);
    }

    private void addListener(final Colour original, final Colour moused) {
        super.addListener(new ClickListener() {

            @Override
            public void eventOccurred(GuiClickEvent event) {
                if (event.isMouseOver()) {
                    iconTexture.setOverrideColour(moused);
                } else if (event.isMouseOff()) {
                    iconTexture.setOverrideColour(original);
                }

            }
        });
    }

}
