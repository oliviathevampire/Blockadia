package net.thegaminghuskymc.sandboxgame.world;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

public interface IBlockAccess {

    int getCombinedLight(BlockPos pos, int lightValue);

    IBlockState getBlockState(BlockPos pos);

    /**
     * Checks to see if an air block exists at the provided location. Note that this only checks to see if the blocks
     * material is set to air, meaning it is possible for non-vanilla blocks to still pass this check.
     */
    boolean isAirBlock(BlockPos pos);

    int getStrongPower(BlockPos pos, EnumFacing direction);

    /**
     * FORGE: isSideSolid, pulled up from {@link World}
     *
     * @param pos      Position
     * @param side     Side
     * @param _default default return value
     * @return if the block is solid on the side
     */
    boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default);
}