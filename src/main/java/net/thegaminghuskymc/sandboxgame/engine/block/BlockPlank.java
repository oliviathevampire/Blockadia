package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockPlank extends BlockCube {

    public BlockPlank(int blockID) {
        super(blockID);
        setRegistryName("plank");
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