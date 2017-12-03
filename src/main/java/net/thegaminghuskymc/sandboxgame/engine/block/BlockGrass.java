package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public class BlockGrass extends BlockCubeOpaque {

    public BlockGrass(int blockID) {
        super(blockID);
    }

    @Override
    public String getUnlocalizedName() {
        return "Grass Block";
    }

    @Override
    public String getRegistryName() {
        return "grass_block";
    }

    @Override
    public void update(Terrain terrain, int x, int y, int z) {

    }

}