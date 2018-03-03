package net.thegaminghuskymc.sandboxgame.client.renderer.color;

import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IItemColor {
    int colorMultiplier(ItemStack stack, int tintIndex);
}