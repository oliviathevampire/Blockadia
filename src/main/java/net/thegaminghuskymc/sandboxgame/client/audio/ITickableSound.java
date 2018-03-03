package net.thegaminghuskymc.sandboxgame.client.audio;

import net.thegaminghuskymc.sandboxgame.client.renderer.texture.ITickable;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ITickableSound extends ISound, ITickable {
    boolean isDonePlaying();
}