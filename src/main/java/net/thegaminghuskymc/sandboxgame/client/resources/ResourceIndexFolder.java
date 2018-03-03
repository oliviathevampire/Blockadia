package net.thegaminghuskymc.sandboxgame.client.resources;

import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.File;

@SideOnly(Side.CLIENT)
public class ResourceIndexFolder extends ResourceIndex {
    private final File baseDir;

    public ResourceIndexFolder(File folder) {
        this.baseDir = folder;
    }

    public File getFile(ResourceLocation location) {
        return new File(this.baseDir, location.toString().replace(':', '/'));
    }

    public File getPackMcmeta() {
        return new File(this.baseDir, "pack.mcmeta");
    }
}