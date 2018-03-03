package net.thegaminghuskymc.sandboxgame.network.play.server;

import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.network.Packet;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sandboxgame.network.play.INetHandlerPlayClient;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.IOException;

public class SPacketAnimation implements Packet<INetHandlerPlayClient> {
    private int entityId;
    private int type;

    public SPacketAnimation() {
    }

    public SPacketAnimation(Entity entityIn, int typeIn) {
        this.entityId = entityIn.getEntityId();
        this.type = typeIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.entityId = buf.readVarInt();
        this.type = buf.readUnsignedByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarInt(this.entityId);
        buf.writeByte(this.type);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleAnimation(this);
    }

    @SideOnly(Side.CLIENT)
    public int getEntityID() {
        return this.entityId;
    }

    @SideOnly(Side.CLIENT)
    public int getAnimationType() {
        return this.type;
    }
}