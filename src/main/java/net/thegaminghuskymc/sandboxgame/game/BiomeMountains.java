package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.world.biome.IBiome;

public class BiomeMountains implements IBiome {

    @Override
    public boolean shouldGenerate(float moisture) {
        return false;
    }

    @Override
    public float getNoiseFrequency() {
        return 2f;
    }

    @Override
    public float getNoiseExponent() {
        return .5f;
    }

}
