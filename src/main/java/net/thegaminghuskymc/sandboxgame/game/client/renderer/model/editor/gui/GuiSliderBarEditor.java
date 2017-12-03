package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiColoredQuad;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiLabel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSliderBar;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiListener;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiSliderBarEventValueChanged;

public class GuiSliderBarEditor extends GuiSliderBar {

	private static final GuiListener<GuiSliderBarEventValueChanged<GuiSliderBarEditor>> LISTENER = new GuiListener<GuiSliderBarEventValueChanged<GuiSliderBarEditor>>() {
		@Override
		public void invoke(GuiSliderBarEventValueChanged<GuiSliderBarEditor> event) {
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

		this.addListener(LISTENER);
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
		float width = this.getPercent();
		this.selected.setBox(0, 0, width, 1, 0);
		this.guiLabel.setText(this.getPrefix() + this.getSelectedValue().toString() + this.getSuffix());
	}

	public void setText(String title) {
		this.guiLabel.setText(title);
	}
}
