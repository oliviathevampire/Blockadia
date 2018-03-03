package net.thegaminghuskymc.sandboxgame.server.dedicated;

import net.thegaminghuskymc.sandboxgame.command.ICommandSender;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class PendingCommand {
    /**
     * The command string.
     */
    public final String command;
    public final ICommandSender sender;

    public PendingCommand(String input, ICommandSender sender) {
        this.command = input;
        this.sender = sender;
    }
}