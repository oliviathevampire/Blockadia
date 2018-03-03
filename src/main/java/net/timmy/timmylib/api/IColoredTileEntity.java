package net.timmy.timmylib.api;

import net.minecraft.item.EnumDyeColor;

public interface IColoredTileEntity {

    /**
     * @return The color of this pool.
     */
    public EnumDyeColor getColor();

    /**
     * Sets the color of this pool.
     * @param color The color to set.
     */
    public void setColor(EnumDyeColor color);

}
