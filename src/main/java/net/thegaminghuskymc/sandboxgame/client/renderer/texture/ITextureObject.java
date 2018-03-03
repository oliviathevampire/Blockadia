package net.thegaminghuskymc.sandboxgame.client.renderer.texture;

import net.thegaminghuskymc.sandboxgame.client.resources.IResourceManager;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public interface ITextureObject {
    void setBlurMipmap(boolean blurIn, boolean mipmapIn);

    void restoreLastBlurMipmap();

    void loadTexture(IResourceManager resourceManager) throws IOException;

    int getGlTextureId();
}