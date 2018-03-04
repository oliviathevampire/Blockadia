package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;


import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventClick<T extends Gui> extends GuiEvent<T> {

	public GuiEventClick(T gui) {
		super(gui);
	}
}
