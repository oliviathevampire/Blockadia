package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.util.math.MathHelper;

import java.util.Random;

public class WorldGenMegaPineTree extends WorldGenHugeTrees {
    private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty(BlockLogs.VARIANT, BlockPlank.EnumType.SPRUCE);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.VARIANT, BlockPlank.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockState PODZOL = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
    private final boolean useBaseHeight;

    public WorldGenMegaPineTree(boolean notify, boolean p_i45457_2_) {
        super(notify, 13, 15, TRUNK, LEAF);
        this.useBaseHeight = p_i45457_2_;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int i = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, i)) {
            return false;
        } else {
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + i, 0, rand);

            for (int j = 0; j < i; ++j) {
                if (isAirLeaves(worldIn, position.up(j))) {
                    this.setBlockAndNotifyAdequately(worldIn, position.up(j), this.woodMetadata);
                }

                if (j < i - 1) {
                    if (isAirLeaves(worldIn, position.add(1, j, 0))) {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(1, j, 0), this.woodMetadata);
                    }

                    if (isAirLeaves(worldIn, position.add(1, j, 1))) {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(1, j, 1), this.woodMetadata);
                    }


                    if (isAirLeaves(worldIn, position.add(0, j, 1))) {
                        this.setBlockAndNotifyAdequately(worldIn, position.add(0, j, 1), this.woodMetadata);
                    }
                }
            }

            return true;
        }
    }

    private void createCrown(World worldIn, int x, int z, int y, int p_150541_5_, Random rand) {
        int i = rand.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
        int j = 0;

        for (int k = y - i; k <= y; ++k) {
            int l = y - k;
            int i1 = p_150541_5_ + MathHelper.floor((float) l / (float) i * 3.5F);
            this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), i1 + (l > 0 && i1 == j && (k & 1) == 0 ? 1 : 0));
            j = i1;
        }
    }

    public void generateSaplings(World worldIn, Random random, BlockPos pos) {
        this.placePodzolCircle(worldIn, pos.west().north());
        this.placePodzolCircle(worldIn, pos.east(2).north());
        this.placePodzolCircle(worldIn, pos.west().south(2));
        this.placePodzolCircle(worldIn, pos.east(2).south(2));

        for (int i = 0; i < 5; ++i) {
            int j = random.nextInt(64);
            int k = j % 8;
            int l = j / 8;

            if (k == 0 || k == 7 || l == 0 || l == 7) {
                this.placePodzolCircle(worldIn, pos.add(-3 + k, 0, -3 + l));
            }
        }
    }

    private void placePodzolCircle(World worldIn, BlockPos center) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    this.placePodzolAt(worldIn, center.add(i, 0, j));
                }
            }
        }
    }

    private void placePodzolAt(World worldIn, BlockPos pos) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockpos = pos.up(i);
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block.canSustainPlant(iblockstate, worldIn, blockpos, EnumFacing.UP, ((BlockSapling) Blocks.SAPLING))) {
                this.setBlockAndNotifyAdequately(worldIn, blockpos, PODZOL);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && i < 0) {
                break;
            }
        }
    }

    //Helper macro
    private boolean isAirLeaves(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }
}