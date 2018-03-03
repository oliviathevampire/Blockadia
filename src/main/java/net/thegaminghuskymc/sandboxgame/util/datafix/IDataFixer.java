package net.thegaminghuskymc.sandboxgame.util.datafix;

import net.thegaminghuskymc.sandboxgame.nbt.NBTTagCompound;

public interface IDataFixer {
    NBTTagCompound process(IFixType type, NBTTagCompound compound, int versionIn);
}