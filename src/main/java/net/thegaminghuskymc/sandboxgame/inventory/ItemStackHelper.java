package net.thegaminghuskymc.sandboxgame.inventory;


import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.nbt.NBTTagCompound;
import net.thegaminghuskymc.sandboxgame.nbt.NBTTagList;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;

import java.util.List;

public class ItemStackHelper {
    public static ItemStack getAndSplit(List<ItemStack> stacks, int index, int amount) {
        return index >= 0 && index < stacks.size() && !((ItemStack) stacks.get(index)).isEmpty() && amount > 0 ? ((ItemStack) stacks.get(index)).splitStack(amount) : ItemStack.EMPTY;
    }

    public static ItemStack getAndRemove(List<ItemStack> stacks, int index) {
        return index >= 0 && index < stacks.size() ? (ItemStack) stacks.set(index, ItemStack.EMPTY) : ItemStack.EMPTY;
    }

    public static NBTTagCompound saveAllItems(NBTTagCompound tag, NonNullList<ItemStack> list) {
        return saveAllItems(tag, list, true);
    }

    public static NBTTagCompound saveAllItems(NBTTagCompound tag, NonNullList<ItemStack> list, boolean saveEmpty) {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);

            if (!itemstack.isEmpty()) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        if (!nbttaglist.hasNoTags() || saveEmpty) {
            tag.setTag("Items", nbttaglist);
        }

        return tag;
    }

    public static void loadAllItems(NBTTagCompound tag, NonNullList<ItemStack> list) {
        NBTTagList nbttaglist = tag.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < list.size()) {
                list.set(j, new ItemStack(nbttagcompound));
            }
        }
    }
}