package net.timmy.timmylib.blocks;


import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.BlockSlab;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.block.state.BlockStateContainer;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.BlockRenderLayer;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.util.math.RayTraceResult;
import net.thegaminghuskymc.sandboxgame.world.IBlockAccess;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.timmy.timmylib.recipie.RecipeHandler;
import net.timmy.timmylib.utils.ProxyRegistry;

import java.util.HashMap;
import java.util.Random;

public abstract class BlockModSlab extends BlockSlab implements IModBlock {

    private static boolean tempDoubleSlab;
    private static HashMap<BlockModSlab, BlockModSlab> halfSlabs = new HashMap<>();
    private static HashMap<BlockModSlab, BlockModSlab> fullSlabs = new HashMap<>();
    private final String[] variants;
    private boolean doubleSlab;
    private String bareName;

    public BlockModSlab(String name, Material materialIn, boolean doubleSlab) {
        super(hacky(materialIn, doubleSlab));
        this.doubleSlab = doubleSlab;
        if (doubleSlab) {
            name = name + "_double";
        }

        this.bareName = name;
        this.variants = new String[]{name};
        this.setUnlocalizedName(name);
        if (!doubleSlab) {
            this.useNeighborBrightness = true;
            this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
        }

        this.setCreativeTab(doubleSlab ? null : CreativeTabs.BUILDING_BLOCKS);
    }

    private static Material hacky(Material m, boolean doubleSlab) {
        tempDoubleSlab = doubleSlab;
        return m;
    }

    public static void initSlab(Block base, int meta, BlockModSlab half, BlockModSlab full) {
        fullSlabs.put(half, full);
        fullSlabs.put(full, full);
        halfSlabs.put(half, half);
        halfSlabs.put(full, half);
        half.register();
        full.register();
        RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(half, 6), "BBB", 'B', ProxyRegistry.newStack(base, 1, meta));
    }

    public BlockStateContainer createBlockState() {
        return tempDoubleSlab ? new BlockStateContainer(this, new IProperty[]{HALF, this.getVariantProp()}) : null;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.doubleSlab ? this.getDefaultState() : this.getDefaultState().withProperty(HALF, meta == 8 ? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM);
    }

    public int getMetaFromState(IBlockState state) {
        if (this.doubleSlab) {
            return 0;
        } else {
            return state.getValue(HALF) == EnumBlockHalf.TOP ? 8 : 0;
        }
    }

    @Override
    public String getBareName() {
        return bareName;
    }

    public BlockSlab getFullBlock() {
        return fullSlabs.get(this);
    }

    public BlockSlab getSingleBlock() {
        return halfSlabs.get(this);
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this.getSingleBlock());
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

    public Item getItemDropped(IBlockState p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(this.getSingleBlock());
    }

    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return super.quantityDropped(state, fortune, random);
    }

    public void register() {
        this.setRegistryName(getPrefix(), bareName);
        ProxyRegistry.register(this);
        if (!this.isDouble()) {
//            ProxyRegistry.register(new ItemModBlockSlab(this, new ResourceLocation(this.getPrefix(), bareName)));
        }

    }

    public String[] getVariants() {
        return this.variants;
    }

    public IProperty[] getIgnoredProperties() {
        return this.doubleSlab ? new IProperty[]{HALF} : new IProperty[]{};
    }

    public String getUnlocalizedName(int meta) {
        return this.getUnlocalizedName();
    }

    public boolean isDouble() {
        return this.doubleSlab;
    }

    public boolean isFullBlock(IBlockState state) {
        return this.isDouble();
    }

    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        IBlockState state = this.getActualState(base_state, world, pos);
        return this.isDouble() || state.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP && side == EnumFacing.UP || state.getValue(BlockSlab.HALF) == EnumBlockHalf.BOTTOM && side == EnumFacing.DOWN;
    }

    public IProperty<?> getVariantProp() {
        return HALF;
    }

    public IProperty<?> getVariantProperty() {
        return HALF;
    }

    public Class getVariantEnum() {
        return EnumBlockHalf.class;
    }

    public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnumBlockHalf.BOTTOM;
    }

}