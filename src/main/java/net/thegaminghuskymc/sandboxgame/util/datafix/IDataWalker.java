package net.thegaminghuskymc.sandboxgame.util.datafix;

import net.thegaminghuskymc.sandboxgame.nbt.NBTTagCompound;

public interface IDataWalker {
    NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn);
}