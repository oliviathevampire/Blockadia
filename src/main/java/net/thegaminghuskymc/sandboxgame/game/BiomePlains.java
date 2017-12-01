package net.thegaminghuskymc.sandboxgame.game;

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
}
