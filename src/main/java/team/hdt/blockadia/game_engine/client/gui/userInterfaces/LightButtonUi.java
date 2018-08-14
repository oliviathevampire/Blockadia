package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;

public class LightButtonUi extends GuiClickable {

    private static final float LIGHT_VALUE = 1.3f;

    private final Colour colour;
    private final Colour lightColour;
    private GuiTexture guiTexture;

    public LightButtonUi(Texture texture, Colour colour) {
        super(1);
        this.guiTexture = new GuiTexture(texture);
        this.guiTexture.setOverrideColour(colour);
        this.colour = colour;
        this.lightColour = colour.duplicate().scale(LIGHT_VALUE);
        addMouseOverEffect();
    }

    @Override
    public void block(boolean block) {
        super.block(block);
        if (block) {
            guiTexture.setOverrideColour(ColourPalette.MIDDLE_GREY);
        } else {
            guiTexture.setOverrideColour(colour);
        }
    }

    @Override
    protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
        super.updateGuiTexturePositions(position, scale);
        guiTexture.setPosition(position.x, position.y, scale.x, scale.y);
    }

    @Override
    protected void getGuiTextures(GuiRenderData data) {
        data.addTexture(getLevel(), guiTexture);
    }

    private void addMouseOverEffect() {
        super.addListener(new ClickListener() {

            @Override
            public void eventOccurred(GuiClickEvent event) {
                if (event.isMouseOver()) {
                    guiTexture.setOverrideColour(lightColour);
                } else if (event.isMouseOff()) {
                    guiTexture.setOverrideColour(colour);
                }

            }
        });
    }

}
