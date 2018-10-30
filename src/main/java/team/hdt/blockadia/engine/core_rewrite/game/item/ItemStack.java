package team.hdt.blockadia.engine.core_rewrite.game.item;

import ga.pheonix.utillib.utils.anouncments.Nullable;
import team.hdt.blockadia.engine.core_rewrite.game.inventory.IInventory;
import team.hdt.blockadia.engine.core_rewrite.handler.Items;
import team.hdt.blockadia.engine.core_rewrite.util.I18n;
import team.hdt.blockadia.engine.core_rewrite.util.ISerializable;
import team.hdt.blockadia.engine.core_rewrite.util.data.ByteDataContainer;


/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A stack of items that can be used in an {@link IInventory} or in other ways.
 * 
 * @author Ocelot5836
 */
public class ItemStack implements ISerializable<ByteDataContainer>, Cloneable {

	/** An empty item stack that the game handles. Used for when a stack should be empty, but not null */
	public static final ItemStack EMPTY = new ItemStack((Item) null);

	private Item item;
	private int count;

	private ItemStack() {
	}

	public ItemStack(Item item) {
		this(item, 1);
	}

	public ItemStack(Item item, int count) {
		this.item = item;
		this.count = count;
	}

	public ItemStack(ByteDataContainer data) {
		this.deserialize(data);
	}

	/**
	 * Decreases the size of the stack by the specified amount.
	 * 
	 * @param amount
	 *            The amount to shrink the stack by
	 */
	public void shrink(int amount) {
		this.grow(-amount);
	}

	/**
	 * Increases the size of the stack by the specified amount.
	 * 
	 * @param amount
	 *            The amount to increase the stack by
	 */
	public void grow(int amount) {
		this.setCount(count + amount);
	}

	/**
	 * @return Whether or not this stack is either {@link ItemStack#EMPTY}, null, or the count is less than or equal to zero
	 */
	public boolean isEmpty() {
		return this == ItemStack.EMPTY || this.item == null || this.count <= 0;
	}

	/**
	 * @return The type of item in this stack
	 */
	@Nullable
	public Item getItem() {
		return item;
	}

	/**
	 * @return The number of items in this stack
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the item in this stack to the specified item if this is not equal to {@link ItemStack#EMPTY}.
	 * 
	 * @param item
	 *            The new item
	 */
	public void setItem(Item item) {
		if (this == ItemStack.EMPTY)
			return;
		this.item = item;
	}

	/**
	 * Sets the number of items in this stack to the specified if this is not equal to {@link ItemStack#EMPTY}.
	 * 
	 * @param count
	 *            The new number of items in this stack
	 */
	public void setCount(int count) {
		if (this == ItemStack.EMPTY)
			return;
		this.count = count;
	}

	/**
	 * Copies this item stack.
	 * 
	 * @return An exact copy of this item stack
	 */
	public ItemStack copy() {
		return this.copy(new ItemStack());
	}

	/**
	 * Copies this itemstack to the specified item stack.
	 * 
	 * @return The specified item stack that now has the same data as this stack
	 */
	public ItemStack copy(ItemStack stack) {
		stack.item = this.item;
		stack.count = this.count;
		return stack;
	}

	@Override
	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		container.setString("id", this.item.getRegistryName().toString());
		container.setInt("c", this.count);
		return container;
	}

	@Override
	public void deserialize(ByteDataContainer data) {
		this.item = Items.byName(data.getString("id"));
		this.count = data.getInt("c");
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.copy();
	}

	@Override
	public int hashCode() {
		return this.item.getRegistryName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemStack) {
			ItemStack stack = (ItemStack) obj;
			return stack.getItem() != null && stack.getItem() == this.getItem();
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return this == ItemStack.EMPTY ? I18n.format("item.empty.name") : this.item == null ? "null" : this.item + "x" + this.count;
	}
}