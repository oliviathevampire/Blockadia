package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSpinner;

public class GuiSpinnerEventSorted<T extends GuiSpinner> extends GuiSpinnerEvent<T> {

    public GuiSpinnerEventSorted(T gui) {
        super(gui);
    }
}
