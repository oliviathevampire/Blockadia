package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator {
    private final Block block;
    private final boolean insideRock;

    public WorldGenHellLava(Block blockIn, boolean insideRockIn) {
        this.block = blockIn;
        this.insideRock = insideRockIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.up()).getBlock() != Blocks.NETHERRACK) {
            return false;
        } else if (!worldIn.isAirBlock(position) && worldIn.getBlockState(position).getBlock() != Blocks.NETHERRACK) {
            return false;
        } else {
            int i = 0;

            if (worldIn.getBlockState(position.west()).getBlock() == Blocks.NETHERRACK) {
                ++i;
            }

            if (worldIn.getBlockState(position.east()).getBlock() == Blocks.NETHERRACK) {
                ++i;
            }

            if (worldIn.getBlockState(position.north()).getBlock() == Blocks.NETHERRACK) {
                ++i;
            }

            if (worldIn.getBlockState(position.south()).getBlock() == Blocks.NETHERRACK) {
                ++i;
            }

            if (worldIn.getBlockState(position.down()).getBlock() == Blocks.NETHERRACK) {
                ++i;
            }

            int j = 0;

            if (worldIn.isAirBlock(position.west())) {
                ++j;
            }

            if (worldIn.isAirBlock(position.east())) {
                ++j;
            }

            if (worldIn.isAirBlock(position.north())) {
                ++j;
            }

            if (worldIn.isAirBlock(position.south())) {
                ++j;
            }

            if (worldIn.isAirBlock(position.down())) {
                ++j;
            }

            if (!this.insideRock && i == 4 && j == 1 || i == 5) {
                IBlockState iblockstate = this.block.getDefaultState();
                worldIn.setBlockState(position, iblockstate, 2);
                worldIn.immediateBlockTick(position, iblockstate, rand);
            }

            return true;
        }
    }
}