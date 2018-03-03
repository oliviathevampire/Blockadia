package net.thegaminghuskymc.sandboxgame.util.datafix;

import net.thegaminghuskymc.sandboxgame.nbt.NBTTagCompound;

public interface IFixableData {
    int getFixVersion();

    NBTTagCompound fixTagCompound(NBTTagCompound compound);
}