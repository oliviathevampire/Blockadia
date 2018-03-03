package net.thegaminghuskymc.sandboxgame.network.play.server;

import net.thegaminghuskymc.sandboxgame.network.Packet;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sandboxgame.network.play.INetHandlerPlayClient;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.IOException;

public class SPacketBlockBreakAnim implements Packet<INetHandlerPlayClient> {
    private int breakerId;
    private BlockPos position;
    private int progress;

    public SPacketBlockBreakAnim() {
    }

    public SPacketBlockBreakAnim(int breakerIdIn, BlockPos positionIn, int progressIn) {
        this.breakerId = breakerIdIn;
        this.position = positionIn;
        this.progress = progressIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.breakerId = buf.readVarInt();
        this.position = buf.readBlockPos();
        this.progress = buf.readUnsignedByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarInt(this.breakerId);
        buf.writeBlockPos(this.position);
        buf.writeByte(this.progress);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleBlockBreakAnim(this);
    }

    @SideOnly(Side.CLIENT)
    public int getBreakerId() {
        return this.breakerId;
    }

    @SideOnly(Side.CLIENT)
    public BlockPos getPosition() {
        return this.position;
    }

    @SideOnly(Side.CLIENT)
    public int getProgress() {
        return this.progress;
    }
}