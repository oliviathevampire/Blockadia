package net.thegaminghuskymc.sandboxgame.world.chunk;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IBlockStatePalette {
    int idFor(IBlockState state);

    /**
     * Gets the block state by the palette id.
     */
    @Nullable
    IBlockState getBlockState(int indexKey);

    @SideOnly(Side.CLIENT)
    void read(PacketBuffer buf);

    void write(PacketBuffer buf);

    int getSerializedSize();
}