package team.hdt.blockadia.engine.core.world.biomes;

import ga.pheonix.utillib.utils.Maths;
import ga.pheonix.utillib.utils.anouncments.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.engine.core.BlockPos;
import team.hdt.blockadia.engine.core.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public abstract class Biome {

    protected static final NoiseGeneratorPerlin TEMPERATURE_NOISE = new NoiseGeneratorPerlin(new Random(1234L), 1);
    protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), 1);
    private static final Logger LOGGER = LogManager.getLogger();
    private final String biomeName;
    /**
     * The base height of this biome. Default 0.1.
     */
    private final float baseHeight;
    /**
     * The variation from the base height of the biome. Default 0.3.
     */
    private final float heightVariation;
    /**
     * The temperature of this biome.
     */
    private final float temperature;
    /**
     * The rainfall in this biome.
     */
    private final float rainfall;
    /**
     * Color tint applied to water depending on biome
     */
    private final int waterColor;
    /**
     * Set to true if snow is enabled for this biome.
     */
    private final boolean enableSnow;
    /**
     * Is true (default) if the biome support rain (DESERT and NETHER can't have rain)
     */
    private final boolean enableRain;
    /**
     * The unique identifier of the biome for which this is a mutation of.
     */
    @Nullable
    private final String baseBiomeRegName;

    public Biome(Biome.BiomeProperties properties) {
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

    public boolean isMutation() {
        return this.baseBiomeRegName != null;
    }

    /**
     * takes temperature, returns color
     */
    public int getSkyColorByTemp(float currentTemperature) {
        currentTemperature = currentTemperature / 3.0F;
        currentTemperature = Maths.clamp(currentTemperature, -1.0F, 1.0F);
        return Maths.hsvToRGB(0.62222224F - currentTemperature * 0.05F, 0.5F + currentTemperature * 0.1F, 1.0F);
    }

    /**
     * Returns true if the biome have snowfall instead a normal rain.
     */
    public boolean getEnableSnow() {
        return this.isSnowyBiome();
    }

    /**
     * Check if rain can occur in biome
     */
    public boolean canRain() {
        return !this.isSnowyBiome() && this.enableRain;
    }

    /**
     * Checks to see if the rainfall level of the biome is extremely high
     */
    public boolean isHighHumidity() {
        return this.getRainfall() > 0.85F;
    }

    /**
     * returns the chance a creature has to spawn.
     */
    public float getSpawningChance() {
        return 0.1F;
    }

    /**
     * Gets the current temperature at the given location, based off of the default for this biome, the elevation of the
     * position, and {@linkplain #TEMPERATURE_NOISE} some random perlin noise.
     */
    public final float getTemperature(BlockPos pos) {
        if (BlockPos.getY() > 64) {
            float f = (float) (TEMPERATURE_NOISE.getValue((double) (BlockPos.getX() / 8.0F), (double) (BlockPos.getZ() / 8.0F)) * 4.0D);
            return this.getDefaultTemperature() - (f + BlockPos.getY() - 64.0F) * 0.05F / 30.0F;
        } else {
            return this.getDefaultTemperature();
        }
    }

    public Class<? extends Biome> getBiomeClass() {
        return this.getClass();
    }

    public Biome.TempCategory getTempCategory() {
        if ((double) this.getDefaultTemperature() < 0.2D) {
            return Biome.TempCategory.COLD;
        } else {
            return (double) this.getDefaultTemperature() < 1.0D ? Biome.TempCategory.MEDIUM : Biome.TempCategory.WARM;
        }
    }

    public boolean ignorePlayerSpawnSuitability() {
        return false;
    }

    public final float getBaseHeight() {
        return this.baseHeight;
    }

    /**
     * Gets a floating point representation of this biome's rainfall
     */
    public final float getRainfall() {
        return this.rainfall;
    }

    public final String getBiomeName() {
        return this.biomeName;
    }

    public final float getHeightVariation() {
        return this.heightVariation;
    }

    /**
     * Gets the constant default temperature for this biome.
     */
    public final float getDefaultTemperature() {
        return this.temperature;
    }

    public final boolean isSnowyBiome() {
        return this.enableSnow;
    }

    public enum TempCategory {
        OCEAN,
        COLD,
        MEDIUM,
        WARM
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

}
