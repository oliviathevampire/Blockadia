package team.hdt.blockadia.old_engine_code_1.core_rewrite.game.inventory;

import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.item.ItemStack;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A super basic inventory that has the basic ability to be filled and emptied.
 * 
 * @author Ocelot5836
 */
public interface IInventory {

	/**
	 * Adds an item stack to the inventory where there is room.
	 * 
	 * @param stack
	 *            The stack to attempt to insert
	 * @return The remaining items in the stack if there was no room
	 */
	ItemStack addStackToInventory(ItemStack stack);

	/**
	 * @return How many item stacks can fit into this inventory
	 */
	int getSize();

	/**
	 * @return Whether or not this inventory is full
	 */
	boolean isFull();

	/**
	 * Checks the inventory for a stack at the specified slot.
	 * 
	 * @param slot
	 *            The slot to get the stack from
	 * @return The stack that was found in that slot or empty if the slot was out of bounds
	 */
	ItemStack getStackInSlot(int slot);

	/**
	 * Sets the stack in the specified slot to the stack provided.
	 * 
	 * @param slot
	 *            The slot to put the stack into
	 * @param stack
	 *            The stack to attempt to put into the slot
	 * @return The stack in that slot or the same stack if the slot was out of bounds
	 */
	ItemStack setStackInSlot(int slot, ItemStack stack);

	/**
	 * Sets all of the stacks in the inventory to {@link ItemStack#EMPTY}.
	 */
	void clear();
}