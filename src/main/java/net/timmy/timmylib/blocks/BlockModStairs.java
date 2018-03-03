package net.timmy.timmylib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.huskylib2.lib.items.blocks.ItemModBlock;
import net.thegaminghuskymc.huskylib2.lib.recipie.RecipeHandler;
import net.thegaminghuskymc.huskylib2.lib.utils.ProxyRegistry;

public abstract class BlockModStairs extends BlockStairs implements IModBlock {
    private final String[] variants;
    private String bareName;

    public BlockModStairs(String name, IBlockState state) {
        super(state);

        variants = new String[]{name};
        bareName = name;

        setUnlocalizedName(name);
        useNeighborBrightness = true;
    }

    public static void initStairs(Block base, int meta, BlockStairs block) {
        RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(block, 4), "B  ", "BB ", "BBB", 'B', ProxyRegistry.newStack(base, 1, meta));
    }

    
    public Block setUnlocalizedName( String name) {
        super.setUnlocalizedName(name);
        setRegistryName(getPrefix(), name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemModBlock(this, new ResourceLocation(this.getPrefix(), name)));
        return this;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.SOLID;
    }

    @Override
    public String getBareName() {
        return bareName;
    }

    public String[] getVariants() {
        return this.variants;
    }

    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

    public EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    public IProperty getVariantProp() {
        return null;
    }

    public Class getVariantEnum() {
        return null;
    }
}