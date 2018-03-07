package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public abstract class GuiParameter<T extends Gui> {

    /**
     * run the param to the given gui
     */
    public abstract void run(T gui);

}
