package net.thegaminghuskymc.sandboxgame.engine.world.generator;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

import java.util.Random;

public class WorldGeneratorHoles extends WorldGenerator {

    @Override
    public void generate(Terrain terrain) {

        Random rng = new Random();

        for (int x = 0; x < Terrain.DIMX; x++) {
            for (int y = 0; y < Terrain.DIMY; y++) {
                for (int z = 0; z < Terrain.DIMZ; z++) {
                    double d = World.NOISE_OCTAVE.noise(
                            (terrain.getWorldPos().x + x * Terrain.BLOCK_SIZE) / (128.0f * Terrain.BLOCK_SIZE),
                            (terrain.getWorldPos().y + y * Terrain.BLOCK_SIZE) / (64.0f * Terrain.BLOCK_SIZE) - 50,
                            (terrain.getWorldPos().z + z * Terrain.BLOCK_SIZE) / (128.0f * Terrain.BLOCK_SIZE));
                    if (d < 0.2f) {
                        terrain.setBlockAt(Blocks.DIRT.get(0), x, y, z);
                    } else {
                        terrain.setBlockAt(Blocks.AIR, x, y, z);
                    }
                }
            }
        }

        for (int x = 0; x < Terrain.DIMX; x++) {
            for (int z = 0; z < Terrain.DIMZ; z++) {
                int y = terrain.getHeightAt(x, z) - 1;
                if (y < 0) {
                    continue;
                }

                double d = World.NOISE_OCTAVE.noise(
                        (terrain.getWorldPos().x + x * Terrain.BLOCK_SIZE) / (16.0f * Terrain.BLOCK_SIZE),
                        (terrain.getWorldPos().z + z * Terrain.BLOCK_SIZE) / (16.0f * Terrain.BLOCK_SIZE));

                if (d < -0.6) {
                    terrain.setBlock(Blocks.PLANTS.get(rng.nextInt(5)), x, y + 1, z);
                }
                terrain.setBlock(Blocks.GRASS.get(0), x, y, z);

            }
        }

//        terrain.generateBigTree(Blocks.LOGS.get(0), Blocks.LEAVES.get(0));

        for(int i = 0; i < 8; i++) {
            terrain.generateHouse(Blocks.PLANKS.get(i), Blocks.LOGS.get(i));
        }

    }
}
