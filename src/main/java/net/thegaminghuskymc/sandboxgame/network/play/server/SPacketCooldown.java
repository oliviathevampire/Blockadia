package net.thegaminghuskymc.sandboxgame.network.play.server;

import net.thegaminghuskymc.sandboxgame.item.Item;
import net.thegaminghuskymc.sandboxgame.network.Packet;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sandboxgame.network.play.INetHandlerPlayClient;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.IOException;

public class SPacketCooldown implements Packet<INetHandlerPlayClient>
{
    private Item item;
    private int ticks;

    public SPacketCooldown()
    {
    }

    public SPacketCooldown(Item itemIn, int ticksIn)
    {
        this.item = itemIn;
        this.ticks = ticksIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.item = Item.getItemById(buf.readVarInt());
        this.ticks = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(Item.getIdFromItem(this.item));
        buf.writeVarInt(this.ticks);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleCooldown(this);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem()
    {
        return this.item;
    }

    @SideOnly(Side.CLIENT)
    public int getTicks()
    {
        return this.ticks;
    }
}