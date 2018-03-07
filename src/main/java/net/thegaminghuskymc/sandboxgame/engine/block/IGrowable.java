package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.engine.world.BlockPos;
import net.thegaminghuskymc.sandboxgame.engine.world.World;

import java.util.Random;

public interface IGrowable {
    /**
     * Whether this IGrowable can grow
     */
    boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient);

    boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state);

    void grow(World worldIn, Random rand, BlockPos pos, IBlockState state);
}