package net.thegaminghuskymc.sandboxgame.world.chunk;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;

interface IBlockStatePaletteResizer {
    int onResize(int bits, IBlockState state);
}