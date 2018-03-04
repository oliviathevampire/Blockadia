package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSliderBar;

public class GuiSliderBarEvent<T extends GuiSliderBar> extends GuiEvent<T> {

	private final float percent;

	public GuiSliderBarEvent(T gui) {
		super(gui);
		this.percent = gui.getPercent();
	}

	public final float getPercent() {
		return (this.percent);
	}

}
