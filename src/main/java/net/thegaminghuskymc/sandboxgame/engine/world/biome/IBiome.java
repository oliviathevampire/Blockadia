package net.thegaminghuskymc.sandboxgame.engine.world.biome;

public interface IBiome {

    /**
     * This should never return just true (unless you want the biome to take over the whole world)
     */
    boolean shouldGenerate(float moisture);

    float getNoiseFrequency();
    float getNoiseExponent();

}
