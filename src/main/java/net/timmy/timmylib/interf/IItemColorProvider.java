package net.timmy.timmylib.interf;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemColorProvider {

    @SideOnly(Side.CLIENT)
    public IItemColor getItemColor();

}
