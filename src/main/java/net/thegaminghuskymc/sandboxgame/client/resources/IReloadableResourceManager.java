package net.thegaminghuskymc.sandboxgame.client.resources;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public interface IReloadableResourceManager extends IResourceManager {
    /**
     * Releases all current resource packs, loads the given list, then triggers all listeners
     */
    void reloadResources(List<IResourcePack> resourcesPacksList);

    /**
     * Registers a listener to be invoked every time the resource manager reloads. NOTE: The listener is immediately
     * invoked once when it is registered.
     */
    void registerReloadListener(IResourceManagerReloadListener reloadListener);
}