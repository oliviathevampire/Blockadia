package team.hdt.blockadia.engine.core_rewrite.gfx.gui.component;

import team.hdt.blockadia.engine.core_rewrite.game.inventory.IInventory;
import team.hdt.blockadia.engine.core_rewrite.game.item.ItemStack;
import team.hdt.blockadia.engine.core_rewrite.gfx.gui.Component;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.ItemStackRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;

public class ComponentInventorySlot extends Component {

	private IInventory inventory;
	private int slot;

	public ComponentInventorySlot(IInventory inventory, int slot, float x, float y) {
		super(x, y, 18, 18);
		this.inventory = inventory;
		this.slot = slot;
	}

	@Override
	protected void render(MasterRenderer renderer, double mouseX, double mouseY, float partialTicks) {
		if (!this.getStack().isEmpty()) {
			ItemStackRenderer.renderItemInGUI(this.getParent(), this.getStack(), this.getX() + 1, this.getY() + 1);
			if (this.isHovered(mouseX, mouseY)) {
				this.getParent().drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 0x4Affffff);
			}
		}
	}

	@Override
	protected void renderOverlay(MasterRenderer renderer, double mouseX, double mouseY, float partialTicks) {
		if (!this.getStack().isEmpty()) {
			if (this.isHovered(mouseX, mouseY)) {
				ItemStackRenderer.renderTooltip(this.getParent(), (float) mouseX, (float) mouseY, this.getStack());
			}
		}
	}

	private ItemStack getStack() {
		return this.inventory.getStackInSlot(this.slot);
	}
}