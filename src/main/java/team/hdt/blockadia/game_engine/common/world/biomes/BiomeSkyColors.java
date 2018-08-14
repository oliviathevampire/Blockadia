package team.hdt.blockadia.game_engine.common.world.biomes;

import com.google.common.collect.Maps;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.IBiome;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;

public class BiomeSkyColors implements Function<IBiome, Color> {

    private final Map<IBiome, Color> biomeColors = Maps.newHashMap();

    public BiomeSkyColors() {
        biomeColors.put(Biomes.DESERT, new Color(0xbea27d));
        biomeColors.put(Biomes.NETHER, new Color(0x781b1b));
    }

    @Override
    public Color apply(IBiome iBiome) {
        return biomeColors.get(iBiome);
    }

}