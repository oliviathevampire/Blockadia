package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventMouseEnter<T extends Gui> extends GuiEventMouse<T> {

    public GuiEventMouseEnter(T gui) {
        super(gui);
    }

}
