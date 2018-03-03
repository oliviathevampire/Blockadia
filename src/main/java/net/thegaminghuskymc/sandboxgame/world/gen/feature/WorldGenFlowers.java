package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenFlowers extends WorldGenerator {
    private BlockFlower flower;
    private IBlockState state;

    public WorldGenFlowers(BlockFlower flowerIn, BlockFlower.EnumFlowerType type) {
        this.setGeneratedBlock(flowerIn, type);
    }

    public void setGeneratedBlock(BlockFlower flowerIn, BlockFlower.EnumFlowerType typeIn) {
        this.flower = flowerIn;
        this.state = flowerIn.getDefaultState().withProperty(flowerIn.getTypeProperty(), typeIn);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 64; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.state)) {
                worldIn.setBlockState(blockpos, this.state, 2);
            }
        }

        return true;
    }
}