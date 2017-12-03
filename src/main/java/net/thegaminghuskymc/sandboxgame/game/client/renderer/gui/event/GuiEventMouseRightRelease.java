package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseRightRelease<T extends Gui> extends GuiEventMouse<T> {

	public GuiEventMouseRightRelease(T gui) {
		super(gui);
	}
}
