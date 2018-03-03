package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.world.IBlockAccess;

import javax.annotation.Nullable;

public interface IBlockColored {

    int getColorMultiplier(IBlockState state, @Nullable IBlockAccess access, @Nullable BlockPos pos, int tintIndex);

}
