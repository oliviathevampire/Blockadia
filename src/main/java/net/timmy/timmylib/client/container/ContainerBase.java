package net.timmy.timmylib.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ContainerBase extends Container {

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }
        for (int slot = 0; slot < 9; slot++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, slot, x + slot * 18, y + 58));
        }
    }

    /*@Override
    public ItemStack slotClick(int index, int mouseButton, ClickType modifier, EntityPlayer player) {
        Slot slot = index < 0 ? null : this.inventorySlots.get(index);

        if (slot instanceof SlotFalseCopy) {
            if (mouseButton == 2) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.putStack(player.inventory.getItemStack().isEmpty() ? ItemStack.EMPTY : player.inventory.getItemStack().copy());
            }

            return player.inventory.getItemStack();
        }

        return super.slotClick(index, mouseButton, modifier, player);
    }*/

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        if (!this.supportsShiftClick(player, slotIndex)) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            if (!performMerge(player, slotIndex, stackInSlot)) {
                return ItemStack.EMPTY;
            }

            slot.onSlotChange(stackInSlot, stack);

            if (stackInSlot.getCount() <= 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.putStack(stackInSlot);
            }

            if (stackInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return stack;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return this.mergeItemStack(this.inventorySlots, stack, startIndex, endIndex, reverseDirection);
    }

    private boolean mergeItemStack(List<Slot> slots, ItemStack stack, int start, int length, boolean reverse) {
        return mergeItemStack(slots, stack, start, length, reverse, true);
    }

    private boolean mergeItemStack(List<Slot> slots, ItemStack stack, int start, int length, boolean r, boolean limit) {
        boolean successful = false;
        int i = !r ? start : length - 1;
        int iterOrder = !r ? 1 : -1;
        Slot slot;
        ItemStack existingStack;

        if (stack.isStackable()) {
            while (stack.getCount() > 0 && (!r && i < length || r && i >= start)) {
                slot = slots.get(i);
                existingStack = slot.getStack();

                if (!existingStack.isEmpty()) {
                    int maxStack = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    int rmv = Math.min(maxStack, stack.getCount());

                    if (slot.isItemValid(this.cloneStack(stack, rmv)) && existingStack.getItem().equals(stack.getItem()) && (!stack.getHasSubtypes() || stack.getItemDamage() == existingStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, existingStack)) {
                        int existingSize = existingStack.getCount() + stack.getCount();

                        if (existingSize <= maxStack) {
                            stack.setCount(0);
                            existingStack.setCount(existingSize);
                            slot.putStack(existingStack);
                            successful = true;
                        } else if (existingStack.getCount() < maxStack) {
                            stack.shrink(maxStack - existingStack.getCount());
                            existingStack.setCount(maxStack);
                            slot.putStack(existingStack);
                            successful = true;
                        }
                    }
                }

                i += iterOrder;
            }
        }

        if (stack.getCount() > 0) {
            i = !r ? start : length - 1;

            while (stack.getCount() > 0 && (!r && i < length || r && i >= start)) {
                slot = slots.get(i);
                existingStack = slot.getStack();

                if (existingStack.isEmpty()) {
                    int maxStack = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    int rmv = Math.min(maxStack, stack.getCount());

                    if (slot.isItemValid(this.cloneStack(stack, rmv))) {
                        existingStack = stack.splitStack(rmv);
                        slot.putStack(existingStack);
                        successful = true;
                    }
                }

                i += iterOrder;
            }
        }

        return successful;
    }

    private ItemStack cloneStack(ItemStack stack, int stackSize) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack retStack = stack.copy();
        retStack.setCount(stackSize);
        return retStack;
    }

    protected boolean performMerge(EntityPlayer player, int slotIndex, ItemStack stack) {
        return this.performMerge(slotIndex, stack);
    }

    protected boolean performMerge(int slotIndex, ItemStack stack) {
        int invBase = getSizeInventory();
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return this.mergeItemStack(stack, invBase, invFull, true);
        }

        return this.mergeItemStack(stack, 0, invBase, false);
    }

    public boolean supportsShiftClick(EntityPlayer player, int slot) {
        return true;
    }

    public abstract int getSizeInventory();

}
