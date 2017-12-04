package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSpinner;

public class GuiSpinnerEventRemoveAll<T extends GuiSpinner> extends GuiSpinnerEvent<T> {

    public GuiSpinnerEventRemoveAll(T gui) {
        super(gui);
    }
}
