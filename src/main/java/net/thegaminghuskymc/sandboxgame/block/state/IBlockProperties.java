package net.thegaminghuskymc.sandboxgame.block.state;

import net.thegaminghuskymc.sandboxgame.block.material.EnumPushReaction;
import net.thegaminghuskymc.sandboxgame.block.material.MapColor;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.util.math.AxisAlignedBB;
import net.thegaminghuskymc.sandboxgame.util.math.Vec3d;
import net.thegaminghuskymc.sandboxgame.world.IBlockAccess;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.EnumBlockRenderType;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.Mirror;
import net.thegaminghuskymc.sandboxgame.util.Rotation;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.util.math.RayTraceResult;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public interface IBlockProperties {
    Material getMaterial();

    boolean isFullBlock();

    boolean canEntitySpawn(Entity entityIn);

    @Deprecated
        //Forge location aware version below
    int getLightOpacity();

    int getLightOpacity(IBlockAccess world, BlockPos pos);

    @Deprecated
        //Forge location aware version below
    int getLightValue();

    int getLightValue(IBlockAccess world, BlockPos pos);

    @SideOnly(Side.CLIENT)
    boolean isTranslucent();

    boolean useNeighborBrightness();

    MapColor getMapColor(IBlockAccess p_185909_1_, BlockPos p_185909_2_);

    /**
     * Returns the blockstate with the given rotation. If inapplicable, returns itself.
     */
    IBlockState withRotation(Rotation rot);

    /**
     * Returns the blockstate mirrored in the given way. If inapplicable, returns itself.
     */
    IBlockState withMirror(Mirror mirrorIn);

    boolean isFullCube();

    @SideOnly(Side.CLIENT)
    boolean hasCustomBreakingProgress();

    EnumBlockRenderType getRenderType();

    @SideOnly(Side.CLIENT)
    int getPackedLightmapCoords(IBlockAccess source, BlockPos pos);

    @SideOnly(Side.CLIENT)
    float getAmbientOcclusionLightValue();

    boolean isBlockNormalCube();

    boolean isNormalCube();

    boolean canProvidePower();

    int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side);

    boolean hasComparatorInputOverride();

    int getComparatorInputOverride(World worldIn, BlockPos pos);

    float getBlockHardness(World worldIn, BlockPos pos);

    float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos);

    int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side);

    EnumPushReaction getMobilityFlag();

    IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos);

    @SideOnly(Side.CLIENT)
    AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos);

    @SideOnly(Side.CLIENT)
    boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing);

    boolean isOpaqueCube();

    @Nullable
    AxisAlignedBB getCollisionBoundingBox(IBlockAccess worldIn, BlockPos pos);

    void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185908_6_);

    AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos);

    RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end);

    /**
     * Determines if the block is solid enough on the top side to support other blocks, like redstone components.
     */
    @Deprecated
    // Forge: Use isSideSolid(IBlockAccess, BlockPos, EnumFacing.UP) instead
    boolean isTopSolid();

    //Forge added functions
    boolean doesSideBlockRendering(IBlockAccess world, BlockPos pos, EnumFacing side);

    boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side);

    boolean doesSideBlockChestOpening(IBlockAccess world, BlockPos pos, EnumFacing side);

    Vec3d getOffset(IBlockAccess access, BlockPos pos);

    boolean causesSuffocation();

    BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockPos pos, EnumFacing facing);
}