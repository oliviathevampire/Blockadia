package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 64; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS && Blocks.PUMPKIN.canPlaceBlockAt(worldIn, blockpos)) {
                worldIn.setBlockState(blockpos, Blocks.PUMPKIN.getDefaultState().withProperty(BlockPumpkin.FACING, EnumFacing.Plane.HORIZONTAL.random(rand)), 2);
            }
        }

        return true;
    }
}