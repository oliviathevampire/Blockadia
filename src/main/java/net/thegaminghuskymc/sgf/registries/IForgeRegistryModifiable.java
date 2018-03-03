package net.thegaminghuskymc.sgf.registries;

import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public interface IForgeRegistryModifiable<V extends IForgeRegistryEntry<V>> extends IForgeRegistry<V>
{
    void clear();
    V remove(ResourceLocation key);
    boolean isLocked();
}