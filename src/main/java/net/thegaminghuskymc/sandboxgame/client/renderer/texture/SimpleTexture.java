package net.thegaminghuskymc.sandboxgame.client.renderer.texture;

import net.thegaminghuskymc.sandboxgame.client.resources.IResource;
import net.thegaminghuskymc.sandboxgame.client.resources.IResourceManager;
import net.thegaminghuskymc.sandboxgame.client.resources.data.TextureMetadataSection;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;

@SideOnly(Side.CLIENT)
public class SimpleTexture extends AbstractTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final ResourceLocation textureLocation;

    public SimpleTexture(ResourceLocation textureResourceLocation) {
        this.textureLocation = textureResourceLocation;
    }

    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        IResource iresource = null;

        try {
            iresource = resourceManager.getResource(this.textureLocation);
            BufferedImage bufferedimage = TextureUtil.readBufferedImage(iresource.getInputStream());
            boolean flag = false;
            boolean flag1 = false;

            if (iresource.hasMetadata()) {
                try {
                    TextureMetadataSection texturemetadatasection = (TextureMetadataSection) iresource.getMetadata("texture");

                    if (texturemetadatasection != null) {
                        flag = texturemetadatasection.getTextureBlur();
                        flag1 = texturemetadatasection.getTextureClamp();
                    }
                } catch (RuntimeException runtimeexception) {
                    LOGGER.warn("Failed reading metadata of: {}", this.textureLocation, runtimeexception);
                }
            }

            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, flag, flag1);
        } finally {
            IOUtils.closeQuietly((Closeable) iresource);
        }
    }
}