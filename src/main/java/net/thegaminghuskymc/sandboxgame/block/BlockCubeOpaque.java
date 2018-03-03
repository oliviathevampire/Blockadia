package net.thegaminghuskymc.sandboxgame.block;

public abstract class BlockCubeOpaque extends BlockCube {

    public BlockCubeOpaque(int blockID) {
        super(blockID);
    }

    @Override
    public boolean isVisible() {
        return (true);
    }

    @Override
    public boolean isOpaque() {
        return (true);
    }

    @Override
    public boolean hasTransparency() {
        return (false);
    }

}