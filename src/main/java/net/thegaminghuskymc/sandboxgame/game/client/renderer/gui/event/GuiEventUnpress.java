package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventUnpress<T extends Gui> extends GuiEvent<T> {

	public GuiEventUnpress(T gui) {
		super(gui);
	}
}
