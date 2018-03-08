package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockPressurPlate extends BlockCube {

    public BlockPressurPlate(int blockID) {
        super(blockID);
        setRegistryName("pressure_plate");
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