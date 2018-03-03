package net.thegaminghuskymc.sandboxgame.client.resources;

import net.thegaminghuskymc.sandboxgame.client.resources.data.IMetadataSection;
import net.thegaminghuskymc.sandboxgame.client.resources.data.MetadataSerializer;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@SideOnly(Side.CLIENT)
public interface IResourcePack {
    InputStream getInputStream(ResourceLocation location) throws IOException;

    boolean resourceExists(ResourceLocation location);

    Set<String> getResourceDomains();

    @Nullable
    <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException;

    BufferedImage getPackImage() throws IOException;

    String getPackName();
}