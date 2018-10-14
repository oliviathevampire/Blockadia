package team.hdt.blockadia.engine.core_rewrite.game.item;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.game.entity.EntityPlayer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.util.I18n;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

import javax.annotation.Nullable;
import java.util.List;

public abstract class Item {

	private Identifier registryName;
	private String unlocalizedName;
	private int maxStackSize;

	public Item(Identifier registryName, String unlocalizedName) {
		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
		this.maxStackSize = 64;
	}

	public void update() {
	}

	public void render(MasterRenderer renderer, double x, double y, double scale) {
	}

	public void onClickUse(EntityPlayer player, int mouseButton, int mouseX, int mouseY) {
	}

	public void onKeyUse(EntityPlayer player, int keyCode) {
	}

	public void addTooltipInformation(List<String> tooltip) {
	}

	public Identifier getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public String getLocalizedName() {
		return I18n.format("item." + this.getUnlocalizedName() + ".name");
	}

	/**
	 * If the texture layer is zero, null is not valid. If the texture layer is greater than zero, then you may pass in null.
	 * 
	 * @param textureLayer
	 *            The layer that is being requested
	 * @return The texture coordinates the item uses
	 */
	@Nullable
	public abstract Vector2f getTextureCoords(int textureLayer);

	/**
	 * @return The resource location for the texture the item uses
	 */
	public abstract Identifier getTexture();

	/**
	 * @return The width (in tiles) of the texture atlas.
	 */
	public int getTextureWidth() {
		return 1;
	}

	protected Item setMaxStackSize(int stackSize) {
		this.maxStackSize = stackSize;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			Item item = (Item) obj;
			return item.getRegistryName().equals(this.getRegistryName());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Item[" + this.getRegistryName() + ":" + this.getLocalizedName() + "]";
	}
}