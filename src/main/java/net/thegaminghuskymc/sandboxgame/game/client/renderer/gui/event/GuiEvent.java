package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public abstract class GuiEvent<T extends Gui> {

    private final T gui;

    public GuiEvent(T gui) {
        this.gui = gui;
    }

    public final T getGui() {
        return (this.gui);
    }
}
