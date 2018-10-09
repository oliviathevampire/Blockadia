package team.hdt.blockadia.engine.core.world.biomes;

import com.google.common.collect.Maps;
import team.hdt.blockadia.engine.core.init.Biomes;
import team.hdt.blockadia.engine.core.world.gen.interfaces.IBiome;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;

public class BiomeFoliageColors implements Function<IBiome, Color> {

    private final Map<IBiome, Color> biomeColors = Maps.newHashMap();

    public BiomeFoliageColors() {
        biomeColors.put(Biomes.DESERT, new Color(0xc09957));
        biomeColors.put(Biomes.NETHER, new Color(0xb42c2c));
    }

    @Override
    public Color apply(IBiome iBiome) {
        return biomeColors.get(iBiome);
    }

}