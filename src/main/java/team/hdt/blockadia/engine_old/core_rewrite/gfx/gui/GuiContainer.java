package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.gui;

import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.inventory.IInventory;

public abstract class GuiContainer extends Gui {

	private IInventory inventory;
	
	public GuiContainer(IInventory inventory) {
		this.inventory = inventory;
		this.addSlots(inventory);
	}
	
	public abstract void addSlots(IInventory inventory);
	
	public IInventory getInventory() {
		return inventory;
	}
}