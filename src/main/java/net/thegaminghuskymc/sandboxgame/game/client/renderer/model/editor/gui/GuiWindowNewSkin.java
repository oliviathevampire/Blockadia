package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiPopUp;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiPrompt;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiText;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiParameter;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;

public class GuiWindowNewSkin extends GuiPopUp {

    public final GuiPrompt name;

    public GuiWindowNewSkin(GuiPopUpCallback<GuiWindowNewSkin> callback) {
        super(callback);

        super.setBox(0.3f, 0.3f, 0.4f, 0.4f, 0.0f);

        GuiParameter<GuiText> txtSize = new GuiTextParameterTextFillBox(0.75f);
        GuiParameter<GuiText> txtCenter = new GuiTextParameterTextCenterBox();

        float w = 0.2f;
        float h = w / 1.6f;

        super.getInfoText().setText("Please enter the name of the new skin");

        this.name = new GuiPrompt();
        this.name.setHint("enter skin name");
        this.name.setBox(0.4f, 0.65f, w, h, 0.0f);
        this.name.setHeldTextColor(Color.WHITE);
        this.name.addTextParameter(txtSize);
        this.name.addTextParameter(txtCenter);
        this.addChild(this.name);
    }
}
