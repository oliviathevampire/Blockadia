package net.thegaminghuskymc.sandboxgame.client.resources.data;

import com.google.gson.JsonDeserializer;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IMetadataSectionSerializer<T extends IMetadataSection> extends JsonDeserializer<T> {
    /**
     * The name of this section type as it appears in JSON.
     */
    String getSectionName();
}