package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSliderBar;

public class GuiSliderBarEvent<T extends GuiSliderBar> extends GuiEvent<T> {

	private final Object selectedObject;
	private final int selectedIndex;

	public GuiSliderBarEvent(T gui) {
		super(gui);
		this.selectedObject = gui.getSelectedValue();
		this.selectedIndex = gui.getSelectedIndex();
	}

	public final Object getSelectedObject() {
		return (this.selectedObject);
	}

	public final int getSelectedIndex() {
		return (this.selectedIndex);
	}

}
