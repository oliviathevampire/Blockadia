package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.world.biome.IBiome;

/**
 * Most basic implementation of a biome
 */
public class BiomePlains implements IBiome {

    @Override
    public boolean shouldGenerate(float moisture) {
        return true;
    }

    @Override
    public float getNoiseFrequency() {
        return 1f;
    }

    @Override
    public float getNoiseExponent() {
        return .5f;
    }

    @Override
    public int getSurfaceThickness() {
        return 3;
    }

    @Override
    public Block getSurfaceBlock() {
        return new Block();
    }

    @Override
    public Block getUndergroundBlock() {
        // Gonna be stone later on
        return new Block();
    }

    @Override
    public Block getZeroBlock() {
        return getSurfaceBlock();
    }
}
