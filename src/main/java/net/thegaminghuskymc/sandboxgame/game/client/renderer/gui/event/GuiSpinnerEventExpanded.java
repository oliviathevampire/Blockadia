package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSpinner;

public class GuiSpinnerEventExpanded<T extends GuiSpinner> extends GuiSpinnerEvent<T> {

    public GuiSpinnerEventExpanded(T gui) {
        super(gui);
    }
}
