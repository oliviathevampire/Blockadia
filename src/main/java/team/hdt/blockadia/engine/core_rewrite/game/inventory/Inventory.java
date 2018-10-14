package team.hdt.blockadia.engine.core_rewrite.game.inventory;

import team.hdt.blockadia.engine.core_rewrite.game.item.ItemStack;
import team.hdt.blockadia.engine.core_rewrite.util.ISerializable;
import team.hdt.blockadia.engine.core_rewrite.util.data.ByteDataContainer;

import java.util.Arrays;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A basic implementation of {@link IInventory}.
 * 
 * @author Ocelot5836
 */
public class Inventory implements IInventory, ISerializable<ByteDataContainer> {

	/** The items that make up the inventory */
	private ItemStack[] items;
	/** The first empty stack available to improve performance. Eg no loops each check unless necessary */
	private int firstEmptySlot;

	public Inventory(int size) {
		this.items = new ItemStack[size];
		this.clear();
		this.firstEmptySlot = -1;
	}

	@Override
	public ItemStack addStackToInventory(ItemStack stack) {
		/** If the stack is empty, don't add anything. */
		if (stack == ItemStack.EMPTY)
			return stack;
		/** Copy the stack so I don't damage the original. */
		ItemStack stackCopy = stack.copy();
		while (!stackCopy.isEmpty() && !this.isFull()) {
			/** Attempt to get a slot with the same stack that has a stack size lower than the item's maximum stack size or the first empty slot found. */
			int slot = this.getApplicableSlot(stackCopy);
			ItemStack stackInSlot = this.getStackInSlot(slot);
			/** If a slot could not be found for the specified stack, stop searching for a slot. */
			if (slot < 0)
				break;
			if (stackInSlot.isEmpty()) {
				/** If the specified stack's size is more than that stack can handle. */
				if (stackCopy.getCount() >= stackCopy.getItem().getMaxStackSize()) {
					/** Set the items at the slot found to the maximum stack size of the stack specified. */
					items[slot] = new ItemStack(stackCopy.getItem(), stackCopy.getItem().getMaxStackSize());
					/** Shrink the stack specified and do this again. */
					stackCopy.shrink(stackCopy.getItem().getMaxStackSize());
				} else {
					/** Set the stack size at the slot to a copy of the original. */
					items[slot] = stackCopy.copy();
					stackCopy.setCount(0);
				}
			} else {
				/** If the specified stack's size as well as the amount of items in the slot is more than that stack can handle. */
				if (stackCopy.getCount() + items[slot].getCount() >= stackCopy.getItem().getMaxStackSize()) {
					/** Get the amount the specified stack should shrink by. */
					int shrinkAmount = items[slot].getItem().getMaxStackSize() - items[slot].getCount();
					/** Max out the stack in the slot. */
					items[slot].setCount(stackCopy.getItem().getMaxStackSize());
					/** Shrink the specified stack by the shrink amount and do this again. */
					stackCopy.shrink(shrinkAmount);
				} else {
					/** Add the specified stack's size to the stack in the slot's stack size. */
					items[slot].grow(stackCopy.getCount());
					stackCopy.setCount(0);
				}
			}
		}
		return stackCopy;
	}

	/**
	 * Checks for the first empty slot if {@link #firstEmptySlot} has a stack in it.
	 * 
	 * @return The first slot that was found to be empty
	 */
	public int getFirstEmptySlot() {
		if (firstEmptySlot < 0 || firstEmptySlot >= items.length || !items[firstEmptySlot].isEmpty()) {
			for (int i = 0; i < items.length; i++) {
				if (items[i].isEmpty()) {
					return i;
				}
			}
			return firstEmptySlot = -1;
		}
		return firstEmptySlot;
	}

	/**
	 * Checks to see if two stacks can merge together.
	 * 
	 * @param stack
	 *            The stack to compare with the stack in the inventory
	 * @return The first slot that can be merged.
	 */
	public int getApplicableSlot(ItemStack stack) {
		for (int i = 0; i < items.length; i++) {
			ItemStack tempStack = items[i];
			if ((tempStack.equals(stack) && tempStack.getCount() < items[i].getItem().getMaxStackSize()) || tempStack.isEmpty()) {
				return i;
			}
		}
		return this.getFirstEmptySlot();
	}

	@Override
	public int getSize() {
		return items.length;
	}

	@Override
	public boolean isFull() {
		for (int i = 0; i < items.length; i++) {
			ItemStack stack = items[i];
			if (stack.isEmpty() || stack.getCount() < items[i].getItem().getMaxStackSize()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < 0 || slot >= items.length)
			return ItemStack.EMPTY;
		return items[slot];
	}

	@Override
	public ItemStack setStackInSlot(int slot, ItemStack stack) {
		if (slot < 0 || slot >= items.length)
			return stack;
		ItemStack returned = items[slot];
		items[slot] = stack;
		return returned;
	}

	@Override
	public void clear() {
		for (int i = 0; i < items.length; i++) {
			items[i] = ItemStack.EMPTY;
		}
	}

	@Override
	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		for (int i = 0; i < items.length; i++) {
			container.setTag(Integer.toString(i), items[i].serialize());
		}
		return container;
	}

	@Override
	public void deserialize(ByteDataContainer data) {
		for (int i = 0; i < items.length; i++) {
			if (data.hasKey(Integer.toString(i), 3)) {
				items[i] = new ItemStack(data.getByteContainer(Integer.toString(i)));
			}
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + Arrays.toString(this.items);
	}
}