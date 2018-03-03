package net.thegaminghuskymc.sandboxgame.world.gen.feature;

import net.thegaminghuskymc.sandboxgame.block.BlockLogs;
import net.thegaminghuskymc.sandboxgame.block.BlockPlank;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenAbstractTree {
    private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty(BlockLogs.VARIANT, BlockPlank.EnumType.SPRUCE);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.VARIANT, BlockPlank.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenTaiga1() {
        super(false);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int i = rand.nextInt(5) + 7;
        int j = i - rand.nextInt(2) - 3;
        int k = i - j;
        int l = 1 + rand.nextInt(k + 1);

        if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
            boolean flag = true;

            for (int i1 = position.getY(); i1 <= position.getY() + 1 + i && flag; ++i1) {
                int j1 = 1;

                if (i1 - position.getY() < j) {
                    j1 = 0;
                } else {
                    j1 = l;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int k1 = position.getX() - j1; k1 <= position.getX() + j1 && flag; ++k1) {
                    for (int l1 = position.getZ() - j1; l1 <= position.getZ() + j1 && flag; ++l1) {
                        if (i1 >= 0 && i1 < 256) {
                            if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(k1, i1, l1))) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);
                boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, EnumFacing.UP, (BlockSapling) Blocks.SAPLING);

                if (isSoil && position.getY() < 256 - i - 1) {
                    state.getBlock().onPlantGrow(state, worldIn, down, position);
                    int k2 = 0;

                    for (int l2 = position.getY() + i; l2 >= position.getY() + j; --l2) {
                        for (int j3 = position.getX() - k2; j3 <= position.getX() + k2; ++j3) {
                            int k3 = j3 - position.getX();

                            for (int i2 = position.getZ() - k2; i2 <= position.getZ() + k2; ++i2) {
                                int j2 = i2 - position.getZ();

                                if (Math.abs(k3) != k2 || Math.abs(j2) != k2 || k2 <= 0) {
                                    BlockPos blockpos = new BlockPos(j3, l2, i2);
                                    state = worldIn.getBlockState(blockpos);

                                    if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos)) {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
                                    }
                                }
                            }
                        }

                        if (k2 >= 1 && l2 == position.getY() + j + 1) {
                            --k2;
                        } else if (k2 < l) {
                            ++k2;
                        }
                    }

                    for (int i3 = 0; i3 < i - 1; ++i3) {
                        BlockPos upN = position.up(i3);
                        state = worldIn.getBlockState(upN);

                        if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN)) {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(i3), TRUNK);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}