package net.timmy.timmylib.interf;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockColorProvider extends IItemColorProvider {

    @SideOnly(Side.CLIENT)
    public IBlockColor getBlockColor();

}
