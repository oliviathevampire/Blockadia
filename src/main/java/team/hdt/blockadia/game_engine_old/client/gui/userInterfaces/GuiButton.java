package team.hdt.blockadia.game_engine_old.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine_old.client.ClientMain;
import team.hdt.blockadia.game_engine_old.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine_old.client.gui.mainGuis.UiSettings;
import team.hdt.blockadia.game_engine_old.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine_old.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine_old.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine_old.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine_old.util.visualFxDrivers.ConstantDriver;
import team.hdt.blockadia.game_engine_old.util.visualFxDrivers.SinWaveDriver;
import team.hdt.blockadia.game_engine_old.util.visualFxDrivers.SlideDriver;
import team.hdt.blockadia.game_engine_old.util.visualFxDrivers.ValueDriver;

import java.util.ArrayList;
import java.util.List;

public class GuiButton extends GuiComponent {

    private static final float TOOL_TIP_TIME = 0.5f;
    private static final int NONE = -1;

    private static final float MAX_SIZE = 1.15f;
    private static final float CHANGE_TIME = 0.07f;

    private int keyBinding = NONE;
    private boolean mustHoldKey = false;

    private GuiTexture texture;
    private boolean mouseOver = false;
    private ValueDriver scaleDriver = new ConstantDriver(1);
    private float currentScale = 1;

    private boolean toggleButton = false;
    private boolean toggledOn = false;
    private boolean manualTurnOffDisabled = false;
    private boolean firstUpdate = true;
    private boolean locked = false;

    private List<Listener> listeners = new ArrayList<Listener>();

    public GuiButton(Texture texture) {
        this.texture = new GuiTexture(texture);
    }

    public GuiButton(Texture texture, boolean toggleButton) {
        this.texture = new GuiTexture(texture);
        this.toggleButton = toggleButton;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void wobble(float factor, float time) {
        scaleDriver = new SinWaveDriver(1, factor, time);
    }

    public void stopWobble() {
        scaleDriver = new SlideDriver(currentScale, 1f, CHANGE_TIME);
    }

    public void lock(boolean locked) {
        if (this.locked == locked) {
            return;
        }
        this.locked = locked;
        if (locked) {
            mouseOffOccurred();
            texture.setOverrideColour(ColourPalette.DARK_GREY);
            texture.setAlphaDriver(new ConstantDriver(UiSettings.PANEL_ALPHA));
        } else {
            texture.setOverrideColour(null);
            texture.setAlphaDriver(new ConstantDriver(1));
        }
    }

    @Override
    public void show(boolean visible) {
        super.show(visible);
        if (!visible) {
            mouseOver = false;
            scaleDriver = new ConstantDriver(1);
        }
    }

    public boolean isToggledOn() {
        return toggledOn;
    }

    public void setKeyBinding(int key, boolean mustHold) {
        this.keyBinding = key;
        this.mustHoldKey = mustHold;
    }

    public GuiTexture getGuiTexture() {
        return texture;
    }

    public void disableManualTurnOff() {
        this.manualTurnOffDisabled = true;
    }

    public void toggle() {
        toggledOn = !toggledOn;
        changeButtonState();
        for (Listener listener : listeners) {
            listener.eventOccurred(toggledOn);
        }
    }

    public void forceToggleOnNoEvent() {
        if (!toggledOn) {
            toggledOn = true;
            changeButtonState();
        }
    }

    @Override
    protected void updateSelf() {
        texture.update();
        if (firstUpdate) {
            firstUpdate = false;
            return;
        }
        if (!locked) {
            checkKeyBinding();
        }
        if (!toggledOn && !locked) {
            dealWithMouseOverEvents();
        }
        if (!locked) {
            checkClickEvent();
        }
        currentScale = scaleDriver.update(ClientMain.getDeltaSeconds());
        updatePositions();
    }

    @Override
    protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
        updatePositions();
    }

    @Override
    protected void getGuiTextures(GuiRenderData data) {
        data.addTexture(getLevel(), texture);
    }

    private void changeButtonState() {
        if (toggledOn) {
            scaleDriver = new ConstantDriver(MAX_SIZE);
            texture.setOverrideColour(ColourPalette.GREEN);
        } else {
            scaleDriver = new SlideDriver(currentScale, 1f, CHANGE_TIME);
            texture.setOverrideColour(null);
        }
    }

    private void checkClickEvent() {
        if (isMouseOver()/* && MyMouse.getActiveMouse().isLeftClick()*/) {
            dealWithButtonAction();
        }
    }

    private void dealWithMouseOverEvents() {
        if (isMouseOver() && !mouseOver) {
            mouseOverOccurred();
        } else if (!isMouseOver() && mouseOver) {
            mouseOffOccurred();
        }
    }

    private void dealWithButtonAction() {
        if (toggleButton) {
            if (!(toggledOn && manualTurnOffDisabled)) {
                toggle();
            }
        } else {
            for (Listener listener : listeners) {
                listener.eventOccurred(true);
            }
        }
    }

    private void mouseOverOccurred() {
        mouseOver = true;
        //SoundMaestro.playSystemSound(MOUSE_OVER);
        scaleDriver = new SlideDriver(currentScale, MAX_SIZE, CHANGE_TIME);
    }

    private void mouseOffOccurred() {
        mouseOver = false;
        scaleDriver = new SlideDriver(currentScale, 1, CHANGE_TIME);
    }

    private void updatePositions() {
        Vectors2f position = super.getPosition();
        Vectors2f scale = super.getScale();
        float currentX = calculateScaledPosition(position.x, scale.x);
        float currentY = calculateScaledPosition(position.y, scale.y);
        float currentScaleX = scale.x * currentScale;
        float currentScaleY = scale.y * currentScale;
        texture.setPosition(currentX, currentY, currentScaleX, currentScaleY);
    }

    private float calculateScaledPosition(float originalPos, float originalScale) {
        float change = (originalScale * currentScale) - originalScale;
        return originalPos - (change / 2);
    }

    private void checkKeyBinding() {
        if (keyBinding == NONE) {
            return;
        }
		/*if (MyKeyboard.getKeyboard().keyDownEventOccurred(keyBinding) && (!toggledOn || !mustHoldKey)) {
			dealWithButtonAction();
		}
		if (mustHoldKey && MyKeyboard.getKeyboard().keyUpEventOccurred(keyBinding) && toggledOn) {
			dealWithButtonAction();
		}*/
    }

}
