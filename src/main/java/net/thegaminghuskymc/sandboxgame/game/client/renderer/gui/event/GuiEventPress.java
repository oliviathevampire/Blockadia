package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventPress<T extends Gui> extends GuiEvent<T> {

	public GuiEventPress(T gui) {
		super(gui);
	}
}
