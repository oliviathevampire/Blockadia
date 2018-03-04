package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiColoredQuad;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiLabel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSliderBar;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiListener;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiSliderBarEventSelect;

public class GuiSliderBarEditor extends GuiSliderBar {

	private static final GuiListener<GuiSliderBarEventSelect<GuiSliderBarEditor>> VALUE_LISTENER = new GuiListener<GuiSliderBarEventSelect<GuiSliderBarEditor>>() {
		@Override
		public void invoke(GuiSliderBarEventSelect<GuiSliderBarEditor> event) {
			event.getGui().onValueChanged();
		}
	};

	private static final Color FILL_COLOR = new Color(0.6f, 0.6f, 1.0f, 1.0f);
	private static final Color BG_COLOR = new Color(0.87f, 0.87f, 0.87f, 1.0f);

	private static final Color FILL_DISABLED_COLOR = Color.scale(null, FILL_COLOR, 0.5f);
	private static final Color BG_DISABLED_COLOR = Color.scale(null, BG_COLOR, 0.5f);

	private GuiColoredQuad total;
	private GuiColoredQuad selected;
	private GuiLabel guiLabel;

	public GuiSliderBarEditor() {
		super();
		this.total = new GuiColoredQuad();
		this.total.setColor(BG_COLOR);
		this.total.setHoverable(false);
		this.addChild(this.total);

		this.selected = new GuiColoredQuad();
		this.selected.setColor(FILL_COLOR);
		this.selected.setHoverable(false);
		this.addChild(this.selected);

		this.guiLabel = new GuiLabel();
		this.guiLabel.setHoverable(false);
		this.guiLabel.setFontColor(0, 0, 0, 1.0f);
		this.guiLabel.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
		this.guiLabel.addTextParameter(new GuiTextParameterTextCenterBox());
		this.addChild(this.guiLabel);

		this.addListener(VALUE_LISTENER);
		this.addListener(ON_PRESS_FOCUS_LISTENER);
	}

	@Override
	protected void onInputUpdate() {
		super.onInputUpdate();
		if (this.isPressed() && this.hasFocus() && this.isEnabled()) {
			this.select(this.getMouseX());
//			System.out.println("IN");
		}
	}

	@Override
	protected void onRender(GuiRenderer guiRenderer) {
		if (this.isEnabled()) {
			this.selected.setColor(FILL_COLOR);
			this.total.setColor(BG_COLOR);
		} else {
			this.total.setColor(BG_DISABLED_COLOR);
			this.selected.setColor(FILL_DISABLED_COLOR);
		}
	}

	protected void onValueChanged() {
		this.selected.setBox(0, 0, this.getPercent(), 1, 0);
		String value = String.valueOf(this.getPercent());
		while (value.length() < 4) {
			value += "0";
		}
		if (value.length() > 4) {
			value = value.substring(0, 4);
		}
		this.guiLabel.setText(this.getPrefix() + value + this.getSuffix());
	}

	public void setText(String title) {
		this.guiLabel.setText(title);
	}
}
