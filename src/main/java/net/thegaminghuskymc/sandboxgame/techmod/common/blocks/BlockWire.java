package net.thegaminghuskymc.sandboxgame.techmod.common.blocks;

import net.thegaminghuskymc.sandboxgame.engine.block.BlockCube;
import net.thegaminghuskymc.sandboxgame.engine.block.state.IBlockState;

public class BlockWire extends BlockCube {

    public BlockWire(int ID) {
        super(ID);
    }

    @Override
    public String getRegistryName() {
        return null;
    }

    @Override
    public String getUnlocalizedName() {
        return null;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean hasTransparency() {
        return false;
    }

}
