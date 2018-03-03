package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenEndGateway extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(position.add(-1, -2, -1), position.add(1, 2, 1))) {
            boolean flag = blockpos$mutableblockpos.getX() == position.getX();
            boolean flag1 = blockpos$mutableblockpos.getY() == position.getY();
            boolean flag2 = blockpos$mutableblockpos.getZ() == position.getZ();
            boolean flag3 = Math.abs(blockpos$mutableblockpos.getY() - position.getY()) == 2;

            if (flag && flag1 && flag2) {
                this.setBlockAndNotifyAdequately(worldIn, new BlockPos(blockpos$mutableblockpos), Blocks.END_GATEWAY.getDefaultState());
            } else if (flag1) {
                this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Blocks.AIR.getDefaultState());
            } else if (flag3 && flag && flag2) {
                this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState());
            } else if ((flag || flag2) && !flag3) {
                this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState());
            } else {
                this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Blocks.AIR.getDefaultState());
            }
        }

        return true;
    }
}