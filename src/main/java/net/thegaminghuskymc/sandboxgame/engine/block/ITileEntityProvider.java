package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.tileentity.TileEntity;
import net.thegaminghuskymc.sandboxgame.engine.world.World;

import javax.annotation.Nullable;

public interface ITileEntityProvider {
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Nullable
    TileEntity createNewTileEntity(World worldIn, int meta);
}