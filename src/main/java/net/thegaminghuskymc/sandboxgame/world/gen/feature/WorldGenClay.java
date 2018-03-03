package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenClay extends WorldGenerator {
    private final Block block = Blocks.CLAY;
    /**
     * The number of blocks to generate.
     */
    private final int numberOfBlocks;

    public WorldGenClay(int p_i2011_1_) {
        this.numberOfBlocks = p_i2011_1_;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position).getMaterial() != Material.WATER) {
            return false;
        } else {
            int i = rand.nextInt(this.numberOfBlocks - 2) + 2;
            int j = 1;

            for (int k = position.getX() - i; k <= position.getX() + i; ++k) {
                for (int l = position.getZ() - i; l <= position.getZ() + i; ++l) {
                    int i1 = k - position.getX();
                    int j1 = l - position.getZ();

                    if (i1 * i1 + j1 * j1 <= i * i) {
                        for (int k1 = position.getY() - 1; k1 <= position.getY() + 1; ++k1) {
                            BlockPos blockpos = new BlockPos(k, k1, l);
                            Block block = worldIn.getBlockState(blockpos).getBlock();

                            if (block == Blocks.DIRT || block == Blocks.CLAY) {
                                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}