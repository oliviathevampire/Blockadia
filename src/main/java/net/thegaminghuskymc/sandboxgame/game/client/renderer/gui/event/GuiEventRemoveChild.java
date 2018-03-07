package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;

public class GuiEventRemoveChild<T extends Gui, U extends Gui> extends GuiEvent<T> {

    private final U child;

    public GuiEventRemoveChild(T gui, U child) {
        super(gui);
        this.child = child;
    }

    public final U getChild() {
        return (this.child);
    }
}
