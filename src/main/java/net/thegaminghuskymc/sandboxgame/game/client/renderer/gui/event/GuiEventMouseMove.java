package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseMove<T extends Gui> extends GuiEventMouse<T> {

	public GuiEventMouseMove(T gui) {
		super(gui);
	}
}
