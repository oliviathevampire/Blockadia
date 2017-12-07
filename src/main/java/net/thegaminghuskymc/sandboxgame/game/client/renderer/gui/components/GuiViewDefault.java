package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;

public class GuiViewDefault extends GuiView {

    private final GuiButton button;
    private final CameraProjective camera;

    public GuiViewDefault(CameraProjective camera) {
        super();
        this.camera = camera;
        this.button = new GuiButton();
        this.button.setFontSize(0.5f, 0.5f);
        this.button.setFontColor(Gui.COLOR_WHITE);
        this.button.addText("Singleplayer");
        this.addChild(this.button);
        this.button.setHoverable(true);
        this.setHoverable(false);
    }

    @Override
    protected void onUpdate() {
        this.updateText();
    }

    private void updateText() {

        camera.getWindow().setCursor(false);

        this.button.setBoxPosition(20.0f, 20.0f - this.button.getTextHeight());
    }

    @Override
    protected void onInitialized(GuiRenderer renderer) {
    }

    @Override
    protected void onDeinitialized(GuiRenderer renderer) {
    }

}