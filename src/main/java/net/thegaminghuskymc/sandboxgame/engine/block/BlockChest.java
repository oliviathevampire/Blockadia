package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockChest extends BlockCube {

    public BlockChest(int blockID) {
        super(blockID);
        setRegistryName("chest");
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