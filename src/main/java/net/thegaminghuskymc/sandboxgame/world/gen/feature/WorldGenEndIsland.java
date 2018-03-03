package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.util.math.MathHelper;

import java.util.Random;

public class WorldGenEndIsland extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        float f = (float) (rand.nextInt(3) + 4);

        for (int i = 0; f > 0.5F; --i) {
            for (int j = MathHelper.floor(-f); j <= MathHelper.ceil(f); ++j) {
                for (int k = MathHelper.floor(-f); k <= MathHelper.ceil(f); ++k) {
                    if ((float) (j * j + k * k) <= (f + 1.0F) * (f + 1.0F)) {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(j, i, k), Blocks.END_STONE.getDefaultState());
                    }
                }
            }

            f = (float) ((double) f - ((double) rand.nextInt(2) + 0.5D));
        }

        return true;
    }
}