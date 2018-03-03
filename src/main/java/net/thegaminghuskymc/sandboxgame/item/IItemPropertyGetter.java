package net.thegaminghuskymc.sandboxgame.item;

import net.thegaminghuskymc.sandboxgame.entity.EntityLivingBase;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IItemPropertyGetter {
    @SideOnly(Side.CLIENT)
    float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn);
}