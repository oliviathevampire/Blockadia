package team.hdt.blockadia.game_engine.core.world.biomes;

import com.google.common.collect.Maps;
import team.hdt.blockadia.game_engine.core.init.Biomes;
import team.hdt.blockadia.game_engine.core.world.gen.interfaces.IBiome;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;

public class BiomeGrassColors implements Function<IBiome, Color> {

    private final Map<IBiome, Color> biomeColors = Maps.newHashMap();

    public BiomeGrassColors() {
        biomeColors.put(Biomes.DESERT, new Color(0xa9834f));
        biomeColors.put(Biomes.NETHER, new Color(0xb42c2c));
    }

    @Override
    public Color apply(IBiome iBiome) {
        return biomeColors.get(iBiome);
    }

}