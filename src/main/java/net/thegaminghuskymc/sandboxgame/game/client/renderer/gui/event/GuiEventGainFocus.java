package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventGainFocus<T extends Gui> extends GuiEvent<T> {

    public GuiEventGainFocus(T gui) {
        super(gui);
    }

}
