package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockCarpet extends BlockCube {

    public BlockCarpet(int blockID) {
        super(blockID);
        setRegistryName("carpet");
    }

    @Override
    public boolean isOpaque() {
        return (false);
    }

    @Override
    public boolean hasTransparency() {
        return (true);
    }

    @Override
    public boolean isVisible() {
        return (true);
    }

}