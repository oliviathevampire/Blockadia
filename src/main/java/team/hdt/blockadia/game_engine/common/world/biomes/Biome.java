package team.hdt.blockadia.game_engine.common.world.biomes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public abstract class Biome {

    private static final Logger LOGGER = LogManager.getLogger();
    private final String biomeName;
    /** The base height of this biome. Default 0.1. */
    private final float baseHeight;
    /** The variation from the base height of the biome. Default 0.3. */
    private final float heightVariation;
    /** The temperature of this biome. */
    private final float temperature;
    /** The rainfall in this biome. */
    private final float rainfall;
    /** Color tint applied to water depending on biome */
    private final int waterColor;
    /** Set to true if snow is enabled for this biome. */
    private final boolean enableSnow;
    /** Is true (default) if the biome support rain (DESERT and NETHER can't have rain) */
    private final boolean enableRain;
    /** The unique identifier of the biome for which this is a mutation of. */
    @Nullable
    private final String baseBiomeRegName;

    public Biome(Biome.BiomeProperties properties)
    {
        this.biomeName = properties.biomeName;
        this.baseHeight = properties.baseHeight;
        this.heightVariation = properties.heightVariation;
        this.temperature = properties.temperature;
        this.rainfall = properties.rainfall;
        this.waterColor = properties.waterColor;
        this.enableSnow = properties.enableSnow;
        this.enableRain = properties.enableRain;
        this.baseBiomeRegName = properties.baseBiomeRegName;
    }

    public static class BiomeProperties {
        private final String biomeName;
        private float baseHeight = 0.1F;
        private float heightVariation = 0.2F;
        private float temperature = 0.5F;
        private float rainfall = 0.5F;
        private int waterColor = 16777215;
        private boolean enableSnow;
        private boolean enableRain = true;
        @Nullable
        private String baseBiomeRegName;

        public BiomeProperties(String nameIn) {
            this.biomeName = nameIn;
        }

        Biome.BiomeProperties temperature(float temperatureIn) {
            if (temperatureIn > 0.1F && temperatureIn < 0.2F) {
                throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
            } else {
                this.temperature = temperatureIn;
                return this;
            }
        }

        Biome.BiomeProperties rainfall(float rainfallIn) {
            this.rainfall = rainfallIn;
            return this;
        }

        Biome.BiomeProperties baseHeight(float baseHeightIn) {
            this.baseHeight = baseHeightIn;
            return this;
        }

        Biome.BiomeProperties heightVariation(float heightVariationIn) {
            this.heightVariation = heightVariationIn;
            return this;
        }

        public Biome.BiomeProperties rainDisabled() {
            this.enableRain = false;
            return this;
        }

        public Biome.BiomeProperties snowEnabled() {
            this.enableSnow = true;
            return this;
        }

        public Biome.BiomeProperties waterColor(int waterColorIn) {
            this.waterColor = waterColorIn;
            return this;
        }

        public Biome.BiomeProperties baseBiome(String nameIn) {
            this.baseBiomeRegName = nameIn;
            return this;
        }
    }

    public enum TempCategory {
        OCEAN,
        COLD,
        MEDIUM,
        WARM
    }

}
