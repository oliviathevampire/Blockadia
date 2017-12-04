package net.thegaminghuskymc.sandboxgame.engine.block;


import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public class BlockLava extends BlockLiquid {

    public BlockLava(int blockID) {
        super(blockID);
    }

    @Override
    public String getRegistryName() {
        return "lava";
    }

    @Override
    public String getUnlocalizedName() {
        return "Lava";
    }

    @Override
    public boolean isOpaque() {
        return (false);
    }

    public boolean hasTransparency() {
        return (true);
    }

    @Override
    public void update(Terrain terrain, int x, int y, int z) {

    }

    @Override
    public void onSet(Terrain terrain, int x, int y, int z) {

    }

}
