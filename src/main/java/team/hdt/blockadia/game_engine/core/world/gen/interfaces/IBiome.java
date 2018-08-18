package team.hdt.blockadia.game_engine.core.world.gen.interfaces;

import team.hdt.blockadia.game_engine.core.registries.RegistryEntry;

import java.util.Map;
import java.util.Random;

public interface IBiome extends RegistryEntry {

    ILayer getLayer(int y, Random random);

    /**
     * Get's the chance of getting a {@link IBiome} generated next to this biome
     *
     * @return a {@link java.util.HashMap} with all the possible neighbour biomes and their chances.
     */
    Map<IBiome, Integer> getChanceMap();

}
