
package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiPopUp;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiPrompt;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiText;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiParameter;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.Bone;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModel;

public class GuiWindowNewBone extends GuiPopUp {

	public final GuiPrompt name;
	public final GuiSpinnerEditor parent;

	public GuiWindowNewBone(EditableModel model, GuiPopUpCallback<GuiWindowNewBone> callback) {
		super(callback);

		super.setBox(0.3f, 0.3f, 0.4f, 0.4f, 0.0f);

		GuiParameter<GuiText> txtSize = new GuiTextParameterTextFillBox(0.75f);
		GuiParameter<GuiText> txtCenter = new GuiTextParameterTextCenterBox();

		float w = 0.2f;
		float h = w / 1.6f;

		super.getInfoText().setText("Please enter the name of the new bone and select it parent");

		this.name = new GuiPrompt();
		this.name.setHint("enter bone name");
		this.name.setBox(0.4f, 0.65f, w, h, 0.0f);
		this.name.setHeldTextColor(Color.WHITE);
		this.name.addTextParameter(txtSize);
		this.name.addTextParameter(txtCenter);
		this.addChild(this.name);

		this.parent = new GuiSpinnerEditor();
		this.parent.add(null);
		for (Bone bone : model.getSkeleton().getBones()) {
			this.parent.add(bone.getName());
		}
		this.parent.setBox(0.4f, 0.45f, w, h, 0.0f);
		this.parent.pick(0);
		this.addChild(this.parent);
	}
}
