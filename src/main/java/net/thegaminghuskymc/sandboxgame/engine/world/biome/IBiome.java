package net.thegaminghuskymc.sandboxgame.engine.world.biome;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;

public interface IBiome {

    /**
     * This should never return just true (unless you want the biome to take over the whole world)
     */
    boolean shouldGenerate(float moisture);

    float getNoiseFrequency();

    float getNoiseExponent();

    int getSurfaceThickness();
    
    Block getSurfaceBlock();

    Block getUndergroundBlock();

    Block getZeroBlock();
}
