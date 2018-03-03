package net.timmy.th2.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.thegaminghuskymc.huskylib2.lib.api.IColoredTileEntity;
import net.thegaminghuskymc.huskylib2.lib.blocks.tile.TileMod;

public class TileEntityCandle extends TileMod implements IColoredTileEntity {

    public EnumDyeColor color = EnumDyeColor.WHITE;

    private static final String TAG_COLOR = "color";

    @Override
    public void writeSharedNBT(NBTTagCompound cmp) {
        cmp.setInteger(TAG_COLOR, color.getMetadata());
    }

    @Override
    public void readSharedNBT(NBTTagCompound cmp) {
        color = EnumDyeColor.byMetadata(cmp.getInteger(TAG_COLOR));
    }

    @Override
    public EnumDyeColor getColor() {
        return color;
    }

    @Override
    public void setColor(EnumDyeColor color) {
        this.color = color;
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 0b1011);
    }

}
