package net.thegaminghuskymc.sandboxgame.engine.world.generator;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

import java.util.Random;

public class WorldGeneratorFlat extends WorldGenerator {

    private Random rng = new Random();

    @Override
    public void generate(Terrain terrain) {
        for (int x = 0; x < Terrain.DIMX; x++) {
            for (int y = 0; y < Terrain.DIMY; y++) {
                terrain.setBlockAt(Blocks.GRASS.getFirst(), x, y, 4);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, 3);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, 2);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, 1);
                terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, 0);
            }
        }

        for (int d = 4; d <= 12; d++) {
            terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), d, 4, 5);
            terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), d, 12, 5);
            terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 4, d, 5);
            terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 12, d, 5);
        }
        terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 8, 8, 5);
        terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 8, 8, 6);
        terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 8, 8, 7);
        terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 8, 8, 8);
        terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 8, 8, 9);
        terrain.setBlockAt(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), 8, 8, 10);

    }
}