package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.tileentity.TileEntity;
import net.thegaminghuskymc.sandboxgame.world.World;

import javax.annotation.Nullable;

public interface ITileEntityProvider {
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Nullable
    TileEntity createNewTileEntity(World worldIn, int meta);
}