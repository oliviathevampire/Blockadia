package net.thegaminghuskymc.sandboxgame.world.chunk;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sandboxgame.util.IntIdentityHashBiMap;
import net.thegaminghuskymc.sandboxgame.world.chunk.IBlockStatePalette;
import net.thegaminghuskymc.sandboxgame.world.chunk.IBlockStatePaletteResizer;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockStatePaletteHashMap implements IBlockStatePalette {
    private final IntIdentityHashBiMap<IBlockState> statePaletteMap;
    private final IBlockStatePaletteResizer paletteResizer;
    private final int bits;

    public BlockStatePaletteHashMap(int bitsIn, IBlockStatePaletteResizer paletteResizerIn) {
        this.bits = bitsIn;
        this.paletteResizer = paletteResizerIn;
        this.statePaletteMap = new IntIdentityHashBiMap<IBlockState>(1 << bitsIn);
    }

    public int idFor(IBlockState state) {
        int i = this.statePaletteMap.getId(state);

        if (i == -1) {
            i = this.statePaletteMap.add(state);

            if (i >= 1 << this.bits) {
                i = this.paletteResizer.onResize(this.bits + 1, state);
            }
        }

        return i;
    }

    /**
     * Gets the block state by the palette id.
     */
    @Nullable
    public IBlockState getBlockState(int indexKey) {
        return this.statePaletteMap.get(indexKey);
    }

    @SideOnly(Side.CLIENT)
    public void read(PacketBuffer buf) {
        this.statePaletteMap.clear();
        int i = buf.readVarInt();

        for (int j = 0; j < i; ++j) {
            this.statePaletteMap.add(Block.BLOCK_STATE_IDS.getByValue(buf.readVarInt()));
        }
    }

    public void write(PacketBuffer buf) {
        int i = this.statePaletteMap.size();
        buf.writeVarInt(i);

        for (int j = 0; j < i; ++j) {
            buf.writeVarInt(Block.BLOCK_STATE_IDS.get(this.statePaletteMap.get(j)));
        }
    }

    public int getSerializedSize() {
        int i = PacketBuffer.getVarIntSize(this.statePaletteMap.size());

        for (int j = 0; j < this.statePaletteMap.size(); ++j) {
            i += PacketBuffer.getVarIntSize(Block.BLOCK_STATE_IDS.get(this.statePaletteMap.get(j)));
        }

        return i;
    }
}