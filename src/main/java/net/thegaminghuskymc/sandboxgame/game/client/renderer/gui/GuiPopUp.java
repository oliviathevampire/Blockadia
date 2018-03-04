
package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiButton;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiLabel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiText;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiWindow;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiParameter;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventPress;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiListener;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiPopUpCallback;

public class GuiPopUp extends GuiWindow {

	private final GuiLabel info;
	private final GuiButton confirm;
	private final GuiButton cancel;

	/**
	 * the callback can be null, then no action is done on confirmed or cancelled
	 */
	public GuiPopUp(@SuppressWarnings("rawtypes") GuiPopUpCallback callback) {
		super();

		super.setBox(0.3f, 0.3f, 0.4f, 0.4f, 0.0f);

		GuiParameter<GuiText> txtSize = new GuiTextParameterTextFillBox(0.75f);
		GuiParameter<GuiText> txtCenter = new GuiTextParameterTextCenterBox();

		float w = 0.2f;
		float h = w / 1.6f;

		this.info = new GuiLabel();
		this.info.setBox(0.0f, 0.8f, 1.0f, h, 0.0f);
		this.info.setFontColor(Color.WHITE);
		this.info.addTextParameter(txtSize);
		this.info.addTextParameter(txtCenter);
		this.info.setText("Pop up");
		this.addChild(info);

		this.confirm = new GuiButton();
		this.confirm.setText("Confirm");
		this.confirm.addTextParameter(txtSize);
		this.confirm.addTextParameter(txtCenter);
		this.confirm.setBox(0.25f, 0.15f, w, h, 0.0f);
		this.addChild(this.confirm);
		this.confirm.addListener(new GuiListener<GuiEventPress<GuiButton>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void invoke(GuiEventPress<GuiButton> event) {
				if (callback != null) {
					callback.onConfirm(GuiPopUp.this);
				}
				close();
			}
		});

		this.cancel = new GuiButton();
		this.cancel.setText("Decline");
		this.cancel.setBox(0.55f, 0.15f, w, h, 0.0f);
		this.cancel.addTextParameter(txtSize);
		this.cancel.addTextParameter(txtCenter);
		this.cancel.addListener(new GuiListener<GuiEventPress<GuiButton>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void invoke(GuiEventPress<GuiButton> event) {
				if (callback != null) {
					callback.onCancel(GuiPopUp.this);
				}
				close();
			}
		});
		this.addChild(this.cancel);
	}

	protected final GuiLabel getInfoText() {
		return (this.info);
	}

	protected final GuiButton getConfirmButton() {
		return (this.confirm);
	}

	protected final GuiButton getCancelButton() {
		return (this.cancel);
	}
}
