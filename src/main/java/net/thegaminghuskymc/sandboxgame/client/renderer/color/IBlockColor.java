package net.thegaminghuskymc.sandboxgame.client.renderer.color;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.world.BlockPos;
import net.thegaminghuskymc.sandboxgame.world.IBlockAccess;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public interface IBlockColor {
    int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex);
}