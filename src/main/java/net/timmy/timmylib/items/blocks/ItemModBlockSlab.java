package net.timmy.timmylib.items.blocks;


import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.timmy.timmylib.blocks.BlockModSlab;
import net.timmy.timmylib.interf.IModBlock;
import net.timmy.timmylib.interf.IVariantHolder;
import net.timmy.timmylib.items.ItemMod;

public class ItemModBlockSlab extends ItemSlab implements IVariantHolder {
    private IModBlock modBlock;

    public ItemModBlockSlab(Block block, ResourceLocation name) {
        super(block, ((BlockModSlab) block).getSingleBlock(), ((BlockModSlab) block).getFullBlock());
        this.modBlock = (IModBlock) block;
        ItemMod.variantHolders.add(this);
        if (this.getVariants().length > 1) {
            this.setHasSubtypes(true);
        }

        this.setRegistryName(name);
    }

    public int getMetadata(int damage) {
        return damage;
    }

    public ItemBlock setUnlocalizedName(String par1Str) {
        return (ItemBlock) super.setUnlocalizedName(par1Str);
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        int dmg = par1ItemStack.getItemDamage();
        String[] variants = this.getVariants();
        String name;
        if (dmg >= variants.length) {
            name = this.modBlock.getBareName();
        } else {
            name = variants[dmg];
        }

        return "block." + name;
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        String[] variants = this.getVariants();
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < variants.length; ++i) {
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