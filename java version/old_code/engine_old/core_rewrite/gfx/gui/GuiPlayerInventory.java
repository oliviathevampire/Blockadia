package team.hdt.blockadia.engine_old.core_rewrite.gfx.gui;

import team.hdt.blockadia.engine_old.core_rewrite.game.inventory.IInventory;
import team.hdt.blockadia.engine_old.core_rewrite.gfx.gui.component.ComponentInventorySlot;
import team.hdt.blockadia.engine_old.core_rewrite.gfx.renderer.MasterRenderer;

public class GuiPlayerInventory extends GuiContainer {

	public GuiPlayerInventory(IInventory inventory) {
		super(inventory);
	}

	@Override
	public void addSlots(IInventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) {
			this.addComponent(new ComponentInventorySlot(inventory, i, i * 20, 0));
		}
	}

	@Override
	public void render(MasterRenderer renderer, double mouseX, double mouseY, float partialTicks) {
		super.drawDefaultBackground(renderer);
		super.render(renderer, mouseX, mouseY, partialTicks);
	}
}