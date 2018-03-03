package net.thegaminghuskymc.sandboxgame.client.resources;

import net.thegaminghuskymc.sandboxgame.client.resources.data.IMetadataSection;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.InputStream;

@SideOnly(Side.CLIENT)
public interface IResource extends Closeable {
    ResourceLocation getResourceLocation();

    InputStream getInputStream();

    boolean hasMetadata();

    @Nullable
    <T extends IMetadataSection> T getMetadata(String sectionName);

    String getResourcePackName();
}