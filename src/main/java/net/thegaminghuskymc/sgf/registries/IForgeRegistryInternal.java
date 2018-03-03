package net.thegaminghuskymc.sgf.registries;

import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public interface IForgeRegistryInternal<V extends IForgeRegistryEntry<V>> extends IForgeRegistry<V>
{
    void setSlaveMap(ResourceLocation name, Object obj);
}