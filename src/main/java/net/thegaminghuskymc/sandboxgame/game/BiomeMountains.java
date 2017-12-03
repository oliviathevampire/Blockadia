package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.world.biome.IBiome;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

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

    @Override
    public int getSurfaceThickness() {
        return 3;
    }

    @Override
    public Block getSurfaceBlock() {
        return Blocks.GRASS;
    }

    @Override
    public Block getUndergroundBlock() {
        return Blocks.GRASS;
    }

    @Override
    public Block getZeroBlock() {
        return getSurfaceBlock();
    }

}
