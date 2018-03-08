package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockWater extends BlockLiquid {

    public BlockWater(int blockID) {
        super(blockID);
        setRegistryName("water");
    }

    @Override
    public String getUnlocalizedName() {
        return "water";
    }

}
