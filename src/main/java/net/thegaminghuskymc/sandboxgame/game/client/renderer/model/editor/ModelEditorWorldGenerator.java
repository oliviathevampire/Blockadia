package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.world.generator.WorldGenerator;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

public class ModelEditorWorldGenerator extends WorldGenerator {

    @Override
    public void generate(Terrain terrain) {
        for (int x = 0; x < Terrain.DIMX; x++) {
            for (int y = 0; y < Terrain.DIMY; y++) {
                // terrain.setBlockAt((x + z) % 2 == 0 ? Blocks.GRASS :
                // Blocks.LOG, x, 4, z);
                terrain.setBlockAt(Blocks.GRASS.getFirst(), x, y, Terrain.DIMZ - 1);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, Terrain.DIMZ - 2);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, Terrain.DIMZ - 3);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, Terrain.DIMZ - 4);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, Terrain.DIMZ - 5);
            }
        }

    }
}
