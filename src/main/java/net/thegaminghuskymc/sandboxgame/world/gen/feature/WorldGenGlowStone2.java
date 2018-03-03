package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenGlowStone2 extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (!worldIn.isAirBlock(position)) {
            return false;
        } else if (worldIn.getBlockState(position.up()).getBlock() != Blocks.NETHERRACK) {
            return false;
        } else {
            worldIn.setBlockState(position, Blocks.GLOWSTONE.getDefaultState(), 2);

            for (int i = 0; i < 1500; ++i) {
                BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));

                if (worldIn.isAirBlock(blockpos)) {
                    int j = 0;

                    for (EnumFacing enumfacing : EnumFacing.values()) {
                        if (worldIn.getBlockState(blockpos.offset(enumfacing)).getBlock() == Blocks.GLOWSTONE) {
                            ++j;
                        }

                        if (j > 1) {
                            break;
                        }
                    }

                    if (j == 1) {
                        worldIn.setBlockState(blockpos, Blocks.GLOWSTONE.getDefaultState(), 2);
                    }
                }
            }

            return true;
        }
    }
}