package net.thegaminghuskymc.sandboxgame.network;

import net.thegaminghuskymc.sandboxgame.util.text.ITextComponent;

public interface INetHandler {
    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    void onDisconnect(ITextComponent reason);
}