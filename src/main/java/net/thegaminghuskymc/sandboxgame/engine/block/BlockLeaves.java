package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockLeaves extends BlockCube {

    public BlockLeaves(int blockID) {
        super(blockID);
    }

    @Override
    public String getRegistryName() {
        return "leaves";
    }

    @Override
    public String getUnlocalizedName() {
        return "Leaves";
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