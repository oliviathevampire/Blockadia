package team.hdt.blockadia.game_engine.common.world.biomes;

import team.hdt.blockadia.game_engine.common.registry.BiomeRegistry;

public class Biomes {

    public static final BiomeFlatlands flatlands = new BiomeFlatlands();
    public static final BiomeDesert desert = new BiomeDesert();

    public static void register(){
        BiomeRegistry.registries.register(flatlands.getIdentifier(), flatlands);
        BiomeRegistry.registries.register(desert.getIdentifier(), desert);
    }
}
