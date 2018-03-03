package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenWaterlily extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 10; ++i) {
            int j = position.getX() + rand.nextInt(8) - rand.nextInt(8);
            int k = position.getY() + rand.nextInt(4) - rand.nextInt(4);
            int l = position.getZ() + rand.nextInt(8) - rand.nextInt(8);

            if (worldIn.isAirBlock(new BlockPos(j, k, l)) && Blocks.WATERLILY.canPlaceBlockAt(worldIn, new BlockPos(j, k, l))) {
                worldIn.setBlockState(new BlockPos(j, k, l), Blocks.WATERLILY.getDefaultState(), 2);
            }
        }

        return true;
    }
}