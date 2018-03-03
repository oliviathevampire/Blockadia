package net.thegaminghuskymc.sandboxgame.client.resources;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.io.File;
import java.io.FileNotFoundException;

@SideOnly(Side.CLIENT)
public class ResourcePackFileNotFoundException extends FileNotFoundException {
    public ResourcePackFileNotFoundException(File resourcePack, String fileName) {
        super(String.format("'%s' in ResourcePack '%s'", fileName, resourcePack));
    }
}