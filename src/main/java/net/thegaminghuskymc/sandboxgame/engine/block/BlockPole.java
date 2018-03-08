package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockPole extends BlockCube {

    public BlockPole(int blockID) {
        super(blockID);
        setRegistryName("pole");
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