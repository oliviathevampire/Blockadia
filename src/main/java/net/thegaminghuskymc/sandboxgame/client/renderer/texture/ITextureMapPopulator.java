package net.thegaminghuskymc.sandboxgame.client.renderer.texture;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ITextureMapPopulator {
    void registerSprites(TextureMap textureMapIn);
}