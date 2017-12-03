package net.thegaminghuskymc.sandboxgame.engine.block;


public class BlockDirt extends BlockCubeOpaque {

    public BlockDirt(int blockID) {
        super(blockID);
    }

    @Override
    public String getRegistryName() {
        return "dirt";
    }

    @Override
    public String getUnlocalizedName() {
        return "Dirt";
    }

}