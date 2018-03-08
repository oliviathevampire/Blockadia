package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockOre extends BlockCube {

    public BlockOre(int blockID) {
        super(blockID);
        setRegistryName("ore");
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