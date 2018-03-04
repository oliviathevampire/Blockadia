package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseHover<T extends Gui> extends GuiEventMouse<T> {

	public GuiEventMouseHover(T gui) {
		super(gui);
	}
}
