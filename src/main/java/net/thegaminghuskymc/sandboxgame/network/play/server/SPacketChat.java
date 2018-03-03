package net.thegaminghuskymc.sandboxgame.network.play.server;

import net.thegaminghuskymc.sandboxgame.network.Packet;
import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;
import net.thegaminghuskymc.sandboxgame.network.play.INetHandlerPlayClient;
import net.thegaminghuskymc.sandboxgame.util.text.ChatType;
import net.thegaminghuskymc.sandboxgame.util.text.ITextComponent;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.IOException;

public class SPacketChat implements Packet<INetHandlerPlayClient> {
    private ITextComponent chatComponent;
    private ChatType type;

    public SPacketChat() {
    }

    public SPacketChat(ITextComponent componentIn) {
        this(componentIn, ChatType.SYSTEM);
    }

    public SPacketChat(ITextComponent message, ChatType type) {
        this.chatComponent = message;
        this.type = type;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.chatComponent = buf.readTextComponent();
        this.type = ChatType.byId(buf.readByte());
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeTextComponent(this.chatComponent);
        buf.writeByte(this.type.getId());
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleChat(this);
    }

    @SideOnly(Side.CLIENT)
    public ITextComponent getChatComponent() {
        return this.chatComponent;
    }

    /**
     * This method returns true if the type is SYSTEM or ABOVE_HOTBAR, and false if CHAT
     */
    public boolean isSystem() {
        return this.type == ChatType.SYSTEM || this.type == ChatType.GAME_INFO;
    }

    public ChatType getType() {
        return this.type;
    }
}