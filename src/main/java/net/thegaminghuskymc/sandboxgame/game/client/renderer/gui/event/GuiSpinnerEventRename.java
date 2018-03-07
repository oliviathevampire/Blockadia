package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSpinner;

public class GuiSpinnerEventRename<T extends GuiSpinner> extends GuiSpinnerEvent<T> {
    private final int index;

    public GuiSpinnerEventRename(T gui, int index) {
        super(gui);
        this.index = index;
    }

    public final int getRenamedObjectIndex() {
        return (this.index);
    }
}
