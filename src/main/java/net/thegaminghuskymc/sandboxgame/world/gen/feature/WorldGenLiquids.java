package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator {
    private final Block block;

    public WorldGenLiquids(Block blockIn) {
        this.block = blockIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.up()).getBlock() != Blocks.STONE) {
            return false;
        } else if (worldIn.getBlockState(position.down()).getBlock() != Blocks.STONE) {
            return false;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(position);

            if (!iblockstate.getBlock().isAir(iblockstate, worldIn, position) && iblockstate.getBlock() != Blocks.STONE) {
                return false;
            } else {
                int i = 0;

                if (worldIn.getBlockState(position.west()).getBlock() == Blocks.STONE) {
                    ++i;
                }

                if (worldIn.getBlockState(position.east()).getBlock() == Blocks.STONE) {
                    ++i;
                }

                if (worldIn.getBlockState(position.north()).getBlock() == Blocks.STONE) {
                    ++i;
                }

                if (worldIn.getBlockState(position.south()).getBlock() == Blocks.STONE) {
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

                if (i == 3 && j == 1) {
                    IBlockState iblockstate1 = this.block.getDefaultState();
                    worldIn.setBlockState(position, iblockstate1, 2);
                    worldIn.immediateBlockTick(position, iblockstate1, rand);
                }

                return true;
            }
        }
    }
}