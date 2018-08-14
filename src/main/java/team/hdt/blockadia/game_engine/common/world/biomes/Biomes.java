package team.hdt.blockadia.game_engine.common.world.biomes;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.registry.BiomeRegistry;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.IBiome;

public class Biomes {

    public static final BiomePlains PLAINS = new BiomePlains();
    public static final BiomeDesert DESERT = new BiomeDesert();
    public static final BiomeDesert NETHER = new BiomeDesert();
    public static final BiomeDesert END = new BiomeDesert();
    public static final BiomeDesert COLD_OCEAN = new BiomeDesert();
    public static final BiomeDesert LUKEWARM_OCEAN = new BiomeDesert();
    public static final BiomeDesert WARM_OCEAN = new BiomeDesert();
    public static final BiomeDesert DEEP_COLD_OCEAN = new BiomeDesert();
    public static final BiomeDesert DEEP_LUKEWARM_OCEAN = new BiomeDesert();
    public static final BiomeDesert DEEP_WARM_OCEAN = new BiomeDesert();
    public static final BiomeDesert FROZEN_OCEAN = new BiomeDesert();
    public static final BiomeDesert SWAMP = new BiomeDesert();
    public static final BiomeDesert PLAIN_HILLS = new BiomeDesert();
    public static final BiomeDesert MESA = new BiomeDesert();
    public static final BiomeForest FOREST = new BiomeForest();

    public static void register(){
        registerBiome("planes", PLAINS);
        registerBiome("desert", DESERT);
        registerBiome("nether", NETHER);
        registerBiome("end_islands", END);
        registerBiome("cold_ocean", COLD_OCEAN);
        registerBiome("lukewarm_ocean", LUKEWARM_OCEAN);
        registerBiome("warm_ocean", WARM_OCEAN);
        registerBiome("deep_cold_ocean", DEEP_COLD_OCEAN);
        registerBiome("deep_lukewarm_ocean", DEEP_LUKEWARM_OCEAN);
        registerBiome("deep_warm_ocean", DEEP_WARM_OCEAN);
        registerBiome("frozen_ocean", FROZEN_OCEAN);
        registerBiome("swamp", SWAMP);
        registerBiome("plain_hills", PLAIN_HILLS);
        registerBiome("mesa", MESA);
        registerBiome("forest", FOREST);
    }

    private static void registerBiome(String name, IBiome biome) {
        BiomeRegistry.registries.register(new Identifier("biome_" + name), biome);
    }

}