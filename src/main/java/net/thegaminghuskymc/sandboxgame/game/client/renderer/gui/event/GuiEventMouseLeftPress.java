package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseLeftPress<T extends Gui> extends GuiEventMouse<T> {

	public GuiEventMouseLeftPress(T gui) {
		super(gui);
	}
}
