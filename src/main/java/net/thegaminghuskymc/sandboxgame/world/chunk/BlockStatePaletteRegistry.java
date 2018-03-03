package net.thegaminghuskymc.sandboxgame.world.chunk;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

public class BlockStatePaletteRegistry implements IBlockStatePalette {
    public int idFor(IBlockState state) {
        int i = Block.BLOCK_STATE_IDS.get(state);
        return i == -1 ? 0 : i;
    }

    /**
     * Gets the block state by the palette id.
     */
    public IBlockState getBlockState(int indexKey) {
        IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(indexKey);
        return iblockstate == null ? Blocks.AIR.getDefaultState() : iblockstate;
    }

    @SideOnly(Side.CLIENT)
    public void read(PacketBuffer buf) {
        buf.readVarInt();
    }

    public void write(PacketBuffer buf) {
        buf.writeVarInt(0);
    }

    public int getSerializedSize() {
        return PacketBuffer.getVarIntSize(0);
    }
}