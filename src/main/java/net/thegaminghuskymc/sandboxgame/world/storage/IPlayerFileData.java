package net.thegaminghuskymc.sandboxgame.world.storage;

import net.thegaminghuskymc.sandboxgame.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public interface IPlayerFileData {
    /**
     * Writes the player data to disk from the specified PlayerEntityMP.
     */
    void writePlayerData(EntityPlayer player);

    /**
     * Reads the player data from disk into the specified PlayerEntityMP.
     */
    @Nullable
    NBTTagCompound readPlayerData(EntityPlayer player);

    /**
     * Returns an array of usernames for which player.dat exists for.
     */
    String[] getAvailablePlayerDat();
}