package net.thegaminghuskymc.sandboxgame.world.chunk;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;

public class ChunkPrimer {
    private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();
    private final char[] data = new char[65536];

    private static int getBlockIndex(int x, int y, int z) {
        return x << 12 | z << 8 | y;
    }

    public IBlockState getBlockState(int x, int y, int z) {
        IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[getBlockIndex(x, y, z)]);
        return iblockstate == null ? DEFAULT_STATE : iblockstate;
    }

    public void setBlockState(int x, int y, int z, IBlockState state) {
        this.data[getBlockIndex(x, y, z)] = (char) Block.BLOCK_STATE_IDS.get(state);
    }

    /**
     * Counting down from the highest block in the sky, find the first non-air block for the given location
     * (actually, looks like mostly checks x, z+1? And actually checks only the very top sky block of actual x, z)
     */
    public int findGroundBlockIdx(int x, int z) {
        int i = (x << 12 | z << 8) + 256 - 1;

        for (int j = 255; j >= 0; --j) {
            IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[i + j]);

            if (iblockstate != null && iblockstate != DEFAULT_STATE) {
                return j;
            }
        }

        return 0;
    }
}