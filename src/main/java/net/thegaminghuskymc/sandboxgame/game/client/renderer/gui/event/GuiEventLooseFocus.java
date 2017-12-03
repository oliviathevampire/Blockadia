package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventLooseFocus<T extends Gui> extends GuiEvent<T> {

	public GuiEventLooseFocus(T gui) {
		super(gui);
	}

}
