package net.timmy.timmylib.items.blocks;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.timmy.timmylib.interf.IModBlock;
import net.timmy.timmylib.interf.IVariantHolder;
import net.timmy.timmylib.items.ItemMod;

public class ItemModBlock extends ItemBlock implements IVariantHolder {
    private IModBlock modBlock;

    public ItemModBlock(Block block, ResourceLocation name) {
        super(block);
        this.modBlock = (IModBlock) block;

        ItemMod.variantHolders.add(this);

        this.setRegistryName(name);
    }

    public int getMetadata(int damage) {
        return damage;
    }

    public String getUnlocalizedName( ItemStack par1ItemStack ) {
        int dmg = par1ItemStack.getItemDamage();
        String[] variants = this.getVariants();
        String name;
        if (dmg >= variants.length) {
            name = this.modBlock.getBareName();
        } else {
            name = variants[ dmg ];
        }

        return "block." + name;
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems ) {
        String[] variants = this.getVariants();
        for (int i = 0; i < variants.length; ++i) {
            if (this.modBlock.shouldDisplayVariant(i)) {
                subItems.add(new ItemStack(this, 1, i));
            }
        }
    }

    public String[] getVariants() {
        return this.modBlock.getVariants();
    }

    public String getModNamespace() {
        return this.modBlock.getModNamespace();
    }
}