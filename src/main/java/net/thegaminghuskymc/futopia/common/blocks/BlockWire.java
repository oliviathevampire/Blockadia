package net.thegaminghuskymc.futopia.common.blocks;

import net.thegaminghuskymc.sandboxgame.engine.block.BlockCube;

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
