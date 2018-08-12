package team.hdt.sandboxgame.game_engine.common.world.gen.interfaces;

import team.hdt.sandboxgame.game_engine.common.registry.RegistryEntry;

import java.util.Map;
import java.util.Random;

public interface IBiome extends RegistryEntry {

    public ILayer getLayer(int y, Random random);

    /**
     * Get's the chancemap for the chance of getting a {@link IBiome} generated next to this biome
     * @return a {@link java.util.HashMap} with all the possible neighbour biomes and their chances.
     */
    public Map<IBiome, Float> getChanceMap();

}
