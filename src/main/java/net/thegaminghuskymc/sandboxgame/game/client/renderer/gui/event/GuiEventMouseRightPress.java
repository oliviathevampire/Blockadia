package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseRightPress<T extends Gui> extends GuiEventMouse<T> {

    public GuiEventMouseRightPress(T gui) {
        super(gui);
    }
}
