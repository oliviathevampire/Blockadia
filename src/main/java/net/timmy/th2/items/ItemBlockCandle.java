package net.timmy.th2.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.huskylib2.lib.items.blocks.ItemModBlock;

public class ItemBlockCandle extends ItemModBlock implements IItemColored {

    public ItemBlockCandle(Block block, ResourceLocation name) {
        super(block, name);
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        int dmg = stack.getItemDamage();
        if(dmg < 0 || dmg >= EnumDyeColor.values().length) return 0xFFFFFFFF;
        return EnumDyeColor.values()[dmg].getColorValue();
    }

}
