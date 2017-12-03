package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public class BlockGrassPath extends BlockCubeOpaque {

    public BlockGrassPath(int blockID) {
        super(blockID);
    }

    @Override
    public String getUnlocalizedName() {
        return "Grass Path";
    }

    @Override
    public String getRegistryName() {
        return "grass_path";
    }

    @Override
    public void update(Terrain terrain, int x, int y, int z) {

    }

}