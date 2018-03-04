package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiLabel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiPrompt;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;

public class GuiPromptEditor extends Gui {

	private GuiLabel info;
	private GuiPrompt prompt;

	public GuiPromptEditor(String textInfo, String hintText) {
		super();

		this.info = new GuiLabel();
		this.info.setBox(0.0f, 0.0f, 0.35f, 1.0f, 0.0f);
		this.info.setHoverable(false);
		this.info.setFontColor(0, 0, 0, 1.0f);
		this.info.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
		this.info.addTextParameter(new GuiTextParameterTextCenterBox());
		this.info.setText(textInfo);
		this.addChild(this.info);

		this.prompt = new GuiPrompt();
		this.prompt.setHeldTextColor(0.0f, 0.0f, 0.0f, 1.0f);
		this.prompt.setHint(hintText);
		this.prompt.setTextTestFormat(GuiPrompt.FORMAT_FLOAT);
		this.prompt.setBox(0.25f, 0.0f, 0.75f, 1.0f, 0.0f);
		this.prompt.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
		this.prompt.addTextParameter(new GuiTextParameterTextCenterBox());
//		this.prompt.addListener(ON_HOVERED_FOCUS_LISTENER);
		this.addChild(this.prompt);
	}

	public final GuiPrompt getPrompt() {
		return (this.prompt);
	}

	public final void setValue(Object v) {
		this.prompt.setHeldText(String.valueOf(v));
	}
}
