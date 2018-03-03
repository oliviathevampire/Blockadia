package net.timmy.timmylib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.huskylib2.lib.utils.ProxyRegistry;

public class BlockModBush extends BlockBush implements IModBlock {

    private final String[] variants;
    private final String bareName;

    public BlockModBush(String name) {
        variants = new String[]{name};
        bareName = name;

        setUnlocalizedName(name);
    }

    
    @Override
    public Block setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        setRegistryName(getPrefix(), name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemBlock(this).setRegistryName(new ResourceLocation(this.getPrefix(), name)));
        return this;
    }

    @Override
    public String getBareName() {
        return bareName;
    }

    @Override
    public String[] getVariants() {
        return variants;
    }

    @Override
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

    @Override
    public EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    @Override
    public IProperty getVariantProp() {
        return null;
    }

    @Override
    public Class getVariantEnum() {
        return null;
    }

}
