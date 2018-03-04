package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseExit<T extends Gui> extends GuiEventMouse<T> {

	public GuiEventMouseExit(T gui) {
		super(gui);
	}
}
