package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public interface IGrowable {
    /**
     * Whether this IGrowable can grow
     */
    boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient);

    boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state);

    void grow(World worldIn, Random rand, BlockPos pos, IBlockState state);
}