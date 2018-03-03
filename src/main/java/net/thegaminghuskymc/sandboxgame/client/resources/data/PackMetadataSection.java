package net.thegaminghuskymc.sandboxgame.client.resources.data;


import net.thegaminghuskymc.sandboxgame.util.text.ITextComponent;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PackMetadataSection implements IMetadataSection {
    private final ITextComponent packDescription;
    private final int packFormat;

    public PackMetadataSection(ITextComponent packDescriptionIn, int packFormatIn) {
        this.packDescription = packDescriptionIn;
        this.packFormat = packFormatIn;
    }

    public ITextComponent getPackDescription() {
        return this.packDescription;
    }

    public int getPackFormat() {
        return this.packFormat;
    }
}