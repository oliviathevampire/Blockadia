package net.thegaminghuskymc.sandboxgame.engine.world.generator;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

import java.util.Random;

public class WorldGeneratorHoles extends WorldGenerator {

    @Override
    public void generate(Terrain terrain) {

        for (int x = 0; x < Terrain.DIMX; x++) {
            for (int y = 0; y < Terrain.DIMY; y++) {
                for (int z = 0; z < Terrain.DIMZ; z++) {
                    double d = World.NOISE_OCTAVE.noise(
                            (terrain.getWorldPos().x + x * Terrain.BLOCK_SIZE) / (64.0f * Terrain.BLOCK_SIZE),
                            (terrain.getWorldPos().y + y * Terrain.BLOCK_SIZE) / (32.0f * Terrain.BLOCK_SIZE),
                            (terrain.getWorldPos().z + z * Terrain.BLOCK_SIZE) / (64.0f * Terrain.BLOCK_SIZE));
                    if (d < 0.2f) {
                        terrain.setBlockAt(Blocks.STONES.getFirst(), x, y, z);
                    } else {
                        terrain.setBlockAt(Blocks.AIR, x, y, z);
                    }
                }
            }
        }

        Random rng = new Random();

        for (int x = 0; x < Terrain.DIMX; x++) {
            for (int y = 0; y < Terrain.DIMY; y++) {
                int z = terrain.getHeightAt(x, y) - 1;
                if (z < 0) {
                    continue;
                }

                double d = World.NOISE_OCTAVE.noise(
                        (terrain.getWorldPos().x + x * Terrain.BLOCK_SIZE) / (16.0f * Terrain.BLOCK_SIZE),
                        (terrain.getWorldPos().y+ y * Terrain.BLOCK_SIZE) / (16.0f * Terrain.BLOCK_SIZE));

                if (d < -0.6) {
                    terrain.setBlock(Blocks.PLANTS.get(rng.nextInt(Blocks.PLANTS.size())), x, y, z + 1);
                }
                terrain.setBlock(Blocks.GRASS.getFirst(), x, y, z);

            }
        }

        int x = rng.nextInt(Terrain.DIMX);
        int y = rng.nextInt(Terrain.DIMY);
        int z = terrain.getHeightAt(x, y);

        if (z != -1) {
            int max = 4 + rng.nextInt(4);
            for (int i = 0; i < max; i++) {
                terrain.setBlock(Blocks.LOGS.get(rng.nextInt(Blocks.LOGS.size())), x, y, z + i);
            }
            for (int dx = -3; dx <= 3; dx++) {
                for (int dy = -3; dy <= 3; dy++) {
                    terrain.setBlock(Blocks.LEAVES.get(rng.nextInt(Blocks.LEAVES.size())), x + dx, y + dy, z + max);
                }
            }
        }
    }
}
