package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseLeftRelease<T extends Gui> extends GuiEventMouse<T> {

	public GuiEventMouseLeftRelease(T gui) {
		super(gui);
	}
}
