package net.thegaminghuskymc.sandboxgame.command;

import com.grillecube.common.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.util.math.Vec3d;
import net.thegaminghuskymc.sandboxgame.util.text.ITextComponent;
import net.thegaminghuskymc.sandboxgame.util.text.TextComponentString;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.server.MinecraftServer;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import javax.annotation.Nullable;

public interface ICommandSender {
    /**
     * Get the name of this object. For players this returns their username
     */
    String getName();

    default ITextComponent getDisplayName() {
        return new TextComponentString(this.getName());
    }

    default void sendMessage(ITextComponent component) {
    }

    /**
     * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
     */
    boolean canUseCommand(int permLevel, String commandName);

    default BlockPos getPosition() {
        return BlockPos.ORIGIN;
    }

    default Vec3d getPositionVector() {
        return Vec3d.ZERO;
    }

    /**
     * Get the world, if available. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return
     * the overworld
     */
    World getEntityWorld();

    @Nullable
    default Entity getCommandSenderEntity() {
        return null;
    }

    default boolean sendCommandFeedback() {
        return false;
    }

    default void setCommandStat(CommandResultStats.Type type, int amount) {
    }

    /**
     * Get the Minecraft server instance
     */
    @Nullable
    MinecraftServer getServer();
}