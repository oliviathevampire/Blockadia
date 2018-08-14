package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.fontRendering.Text;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.MyFile;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ConstantDriver;

import java.util.ArrayList;
import java.util.List;

public class GuiTextButton extends GuiComponent {

    protected static final Texture HIGHLIGHT = Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, "highlight.png"))
            .noFiltering().create();

    private static final float CHANGE_TIME = 0.1f;
    private static final float MAX_SCALE = 1.15f;

    private Text text;
    private Colour blockedColour = new Colour(0.7f, 0.7f, 0.7f);
    private Colour textColour;
    private boolean mouseOver = false;
    private boolean blocked = false;
    private GuiTexture highlight;
    private boolean highlighted = false;

    private List<Listener> listeners = new ArrayList<Listener>();

    public GuiTextButton(Text text) {
        this.text = text;
        this.textColour = text.getColour();
        super.addText(text, 0, 0, 1);
        this.highlight = new GuiTexture(HIGHLIGHT);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void block() {
        if (!blocked) {
            text.setColour(blockedColour);
            mouseOver = false;
            blocked = true;
            highlighted = false;
            text.setScaleDriver(new ConstantDriver(1));
        }
    }

    public void unblock() {
        if (blocked) {
            blocked = false;
            text.setColour(textColour);
        }
    }

    public void highlight(boolean select) {
        this.highlighted = select;
        if (highlighted) {
            text.setColour(ColourPalette.WHITE);
            //text.setScaleDriver(new ConstantDriver(MAX_SCALE));
        } else {
            //text.setColour(ColourPalette.WHITE);
            //text.setScaleDriver(new SlideDriver(text.getScale(), 1f, CHANGE_TIME));
        }
    }

    @Override
    protected void updateSelf() {
        if (blocked || highlighted) {
            return;
        }
        if (isMouseOver() && !mouseOver) {
            text.setColour(ColourPalette.GREEN);
            //text.setScaleDriver(new SlideDriver(text.getScale(), MAX_SCALE, CHANGE_TIME));
            mouseOver = true;
            //SoundMaestro.playSystemSound(GuiSounds.MOUSE_OVER);
        } else if (!isMouseOver() && mouseOver) {
            //text.setScaleDriver(new SlideDriver(text.getScale(), 1f, CHANGE_TIME));
            text.setColour(ColourPalette.WHITE);
            mouseOver = false;
        }
		/*if (isMouseOver() && MyMouse.getActiveMouse().isLeftClick()) {
			SoundMaestro.playSystemSound(GuiSounds.getClickSound());
			for (Listener listener : listeners) {
				listener.eventOccurred(true);
			}
		}*/
    }

    @Override
    protected void getGuiTextures(GuiRenderData data) {
        if (highlighted) {
            data.addTexture(getLevel(), highlight);
        }
    }

    @Override
    protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
        highlight.setPosition(position.x, position.y, scale.x, scale.y);
    }

}
