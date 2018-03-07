package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSliderBar;

public class GuiSliderBarEventSelect<T extends GuiSliderBar> extends GuiSliderBarEvent<T> {

    public GuiSliderBarEventSelect(T gui) {
        super(gui);
    }

}
