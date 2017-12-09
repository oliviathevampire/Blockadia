package net.thegaminghuskymc.sandboxgame.engine.world.generator;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

public class WorldGeneratorFlat extends WorldGenerator {

    @Override
    public void generate(Terrain terrain) {
        for (int x = 0; x < Terrain.DIMX; x++) {
            for (int z = 0; z < Terrain.DIMZ; z++) {
                terrain.setBlockAt(Blocks.GRASS.get(0), x, Terrain.DIMY - 1, z);
				terrain.setBlockAt(Blocks.DIRT.get(0), x, Terrain.DIMY - 2, z);
				terrain.setBlockAt(Blocks.DIRT.get(0), x, Terrain.DIMY - 3, z);
				terrain.setBlockAt(Blocks.DIRT.get(0), x, Terrain.DIMY - 4, z);
				terrain.setBlockAt(Blocks.DIRT.get(0), x, Terrain.DIMY - 5, z);
            }
        }
    }
}