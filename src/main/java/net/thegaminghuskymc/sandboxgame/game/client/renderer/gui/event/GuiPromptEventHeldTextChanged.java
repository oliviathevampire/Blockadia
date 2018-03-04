package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiPrompt;

public class GuiPromptEventHeldTextChanged<T extends GuiPrompt> extends GuiEvent<T> {
	public GuiPromptEventHeldTextChanged(T gui) {
		super(gui);
	}
}
