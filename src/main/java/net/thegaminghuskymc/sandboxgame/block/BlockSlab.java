package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.MapColor;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.block.properties.PropertyEnum;
import net.thegaminghuskymc.sandboxgame.block.state.BlockFaceShape;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.entity.EntityLivingBase;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.math.AxisAlignedBB;
import net.thegaminghuskymc.sandboxgame.world.IBlockAccess;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sgf.common.ForgeModContainer;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockSlab extends Block {
    public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.<BlockSlab.EnumBlockHalf>create("half", BlockSlab.EnumBlockHalf.class);
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockSlab(Material materialIn) {
        this(materialIn, materialIn.getMaterialMapColor());
    }

    public BlockSlab(Material p_i47249_1_, MapColor p_i47249_2_) {
        super(p_i47249_1_, p_i47249_2_);
        this.fullBlock = this.isDouble();
        this.setLightOpacity(255);
    }

    @SideOnly(Side.CLIENT)
    protected static boolean isHalfSlab(IBlockState state) {
        Block block = state.getBlock();
        return block == Blocks.STONE_SLAB;
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (this.isDouble()) {
            return FULL_BLOCK_AABB;
        } else {
            return state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF;
        }
    }

    /**
     * Determines if the block is solid enough on the top side to support other blocks, like redstone components.
     */
    public boolean isTopSolid(IBlockState state) {
        return ((BlockSlab) state.getBlock()).isDouble() || state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
    }

    /**
     * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
     * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
     * does not fit the other descriptions and will generally cause other things not to connect to the face.
     *
     * @return an approximation of the form of the given face
     */
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (((BlockSlab) state.getBlock()).isDouble()) {
            return BlockFaceShape.SOLID;
        } else if (face == EnumFacing.UP && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            return BlockFaceShape.SOLID;
        } else {
            return face == EnumFacing.DOWN && state.getValue(HALF) == BlockSlab.EnumBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        }
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state) {
        return this.isDouble();
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        if (ForgeModContainer.disableStairSlabCulling)
            return super.doesSideBlockRendering(state, world, pos, face);

        if (state.isOpaqueCube())
            return true;

        EnumBlockHalf side = state.getValue(HALF);
        return (side == EnumBlockHalf.TOP && face == EnumFacing.UP) || (side == EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN);
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);

        if (this.isDouble()) {
            return iblockstate;
        } else {
            return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate : iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.TOP);
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random) {
        return this.isDouble() ? 2 : 1;
    }

    public boolean isFullCube(IBlockState state) {
        return this.isDouble();
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        if (this.isDouble()) {
            return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        } else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side)) {
            return false;
        } else if (false) // Forge: Additional logic breaks doesSideBlockRendering and is no longer useful.
        {
            IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
            boolean flag = isHalfSlab(iblockstate) && iblockstate.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
            boolean flag1 = isHalfSlab(blockState) && blockState.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;

            if (flag1) {
                if (side == EnumFacing.DOWN) {
                    return true;
                } else if (side == EnumFacing.UP && super.shouldSideBeRendered(blockState, blockAccess, pos, side)) {
                    return true;
                } else {
                    return !isHalfSlab(iblockstate) || !flag;
                }
            } else if (side == EnumFacing.UP) {
                return true;
            } else if (side == EnumFacing.DOWN && super.shouldSideBeRendered(blockState, blockAccess, pos, side)) {
                return true;
            } else {
                return !isHalfSlab(iblockstate) || flag;
            }
        }
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    /**
     * Returns the slab block name with the type associated with it
     */
    public abstract String getUnlocalizedName(int meta);

    public abstract boolean isDouble();

    public abstract IProperty<?> getVariantProperty();

    public abstract Comparable<?> getTypeForItem(ItemStack stack);

    public enum EnumBlockHalf implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        private EnumBlockHalf(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }
}