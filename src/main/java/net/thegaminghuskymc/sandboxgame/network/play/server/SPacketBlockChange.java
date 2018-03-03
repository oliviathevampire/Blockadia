package net.thegaminghuskymc.sandboxgame.network.play.server;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.network.Packet;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sandboxgame.network.play.INetHandlerPlayClient;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.IOException;

public class SPacketBlockChange implements Packet<INetHandlerPlayClient> {
    public IBlockState blockState;
    private BlockPos blockPosition;

    public SPacketBlockChange() {
    }

    public SPacketBlockChange(World worldIn, BlockPos posIn) {
        this.blockPosition = posIn;
        this.blockState = worldIn.getBlockState(posIn);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.blockPosition = buf.readBlockPos();
        this.blockState = Block.BLOCK_STATE_IDS.getByValue(buf.readVarInt());
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeBlockPos(this.blockPosition);
        buf.writeVarInt(Block.BLOCK_STATE_IDS.get(this.blockState));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleBlockChange(this);
    }

    @SideOnly(Side.CLIENT)
    public IBlockState getBlockState() {
        return this.blockState;
    }

    @SideOnly(Side.CLIENT)
    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }
}