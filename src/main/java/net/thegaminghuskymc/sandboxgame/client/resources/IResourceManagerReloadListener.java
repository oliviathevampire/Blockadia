package net.thegaminghuskymc.sandboxgame.client.resources;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IResourceManagerReloadListener {
    void onResourceManagerReload(IResourceManager resourceManager);
}