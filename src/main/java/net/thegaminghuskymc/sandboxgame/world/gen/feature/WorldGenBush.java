package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.block.BlockBush;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenBush extends WorldGenerator {
    private final BlockBush block;

    public WorldGenBush(BlockBush blockIn) {
        this.block = blockIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 64; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < worldIn.getHeight() - 1) && this.block.canBlockStay(worldIn, blockpos, this.block.getDefaultState())) {
                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
            }
        }

        return true;
    }
}