package team.hdt.blockadia.game_engine.common.world.block;

import com.google.common.collect.ImmutableMap;
import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.entity.BaseEntity;
import team.hdt.blockadia.game_engine.common.registry.RegistryEntry;
import team.hdt.blockadia.game_engine.common.util.EnumBlockRenderType;
import team.hdt.blockadia.game_engine.common.util.EnumFacing;
import team.hdt.blockadia.game_engine.common.util.Mirror;
import team.hdt.blockadia.game_engine.common.util.Rotation;
import team.hdt.blockadia.game_engine.common.util.collision.AxisAlignedBB;
import team.hdt.blockadia.game_engine.common.util.interfaces.Nonnull;
import team.hdt.blockadia.game_engine.common.util.math.BlockPos;
import team.hdt.blockadia.game_engine.common.util.math.RayTraceResult;
import team.hdt.blockadia.game_engine.common.util.math.Vec3d;
import team.hdt.blockadia.game_engine.common.world.World;
import team.hdt.blockadia.game_engine.common.world.block.material.BlockMaterials;
import team.hdt.blockadia.game_engine.common.world.block.material.MapColor;
import team.hdt.blockadia.game_engine.common.world.block.properties.IProperty;
import team.hdt.blockadia.game_engine.common.world.block.state.BlockFaceShape;
import team.hdt.blockadia.game_engine.common.world.block.state.BlockStateContainer;
import team.hdt.blockadia.game_engine.common.world.block.state.IBlockState;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class BlockType implements RegistryEntry, IBlockState {

    private final boolean fullBlock;
    private final MapColor mapColor;
    private final BlockMaterials.Material material;
    private Identifier registryIdentifier;
    protected final BlockStateContainer blockState;
    private IBlockState defaultBlockState;
    private int lightOpacity;

    public BlockType(BlockMaterials.Material material, MapColor mapColor) {
        this.material = material;
        this.mapColor = mapColor;
        this.blockState = this.createBlockState();
        this.setDefaultState(this.blockState.getBaseState());
        this.fullBlock = this.getDefaultState().isOpaqueCube();
        this.lightOpacity = this.fullBlock ? 255 : 0;
    }

    /**
     * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
     */
    public BlockType setLightOpacity(int opacity)
    {
        this.lightOpacity = opacity;
        return this;
    }

    @Nonnull
    @Override
    public Identifier getIdentifier() {
        if (this.registryIdentifier == null) {
            throw new IllegalStateException("Cannot get identifier of entry not yet registered");
        }
        return this.registryIdentifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        if (this.registryIdentifier != null) {
            throw new IllegalStateException("Cannot set registry identifier twice");
        }
        this.registryIdentifier = identifier;
    }

    @Override
    public String toString() {
        if (this.registryIdentifier == null) {
            return super.toString();
        }
        return "Block{id=" + this.registryIdentifier + "}";
    }

    @Override
    public Collection<IProperty<?>> getPropertyKeys() {
        return null;
    }

    @Override
    public <T extends Comparable<T>> T getValue(IProperty<T> property) {
        return null;
    }

    @Override
    public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
        return null;
    }

    @Override
    public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
        return null;
    }

    @Override
    public ImmutableMap<IProperty<?>, Comparable<?>> getProperties() {
        return null;
    }

    @Override
    public BlockType getBlock() {
        return null;
    }

    @Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param) {
        return false;
    }

    @Override
    public void neighborChanged(World worldIn, BlockPos pos, BlockType blockIn, BlockPos fromPos) {

    }

    @Override
    public BlockMaterials.Material getMaterial() {
        return material;
    }

    @Override
    public boolean isFullBlock() {
        return fullBlock;
    }

    @Override
    public boolean canEntitySpawn(BaseEntity entityIn) {
        return false;
    }

    @Override
    public int getLightOpacity() {
        return lightOpacity;
    }

    @Override
    public int getLightOpacity(IBlockAccess world, BlockPos pos) {
        return lightOpacity;
    }

    @Override
    public int getLightValue() {
        return 0;
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos) {
        return 0;
    }

    @Override
    public boolean isTranslucent() {
        return false;
    }

    @Override
    public boolean useNeighborBrightness() {
        return false;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this);
    }

    public BlockStateContainer getBlockState()
    {
        return this.blockState;
    }

    protected final void setDefaultState(IBlockState state)
    {
        this.defaultBlockState = state;
    }

    public final IBlockState getDefaultState()
    {
        return this.defaultBlockState;
    }

    @Override
    public MapColor getMapColor(IBlockAccess p_185909_1_, BlockPos p_185909_2_) {
        return mapColor;
    }

    @Override
    public IBlockState withRotation(Rotation rot) {
        return null;
    }

    @Override
    public IBlockState withMirror(Mirror mirrorIn) {
        return null;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean hasCustomBreakingProgress() {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return null;
    }

    @Override
    public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos) {
        return 0;
    }

    @Override
    public float getAmbientOcclusionLightValue() {
        return 0;
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean canProvidePower() {
        return false;
    }

    @Override
    public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return false;
    }

    @Override
    public int getComparatorInputOverride(World worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public float getBlockHardness(World worldIn, BlockPos pos) {
        return 0;
    }

    /*@Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos) {
        return 0;
    }*/

    @Override
    public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable BaseEntity entityIn, boolean p_185908_6_) {

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
        return null;
    }

    @Override
    public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return null;
    }

    @Override
    public boolean isTopSolid() {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean doesSideBlockChestOpening(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public Vec3d getOffset(IBlockAccess access, BlockPos pos) {
        return null;
    }

    @Override
    public boolean causesSuffocation() {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
        return null;
    }

}