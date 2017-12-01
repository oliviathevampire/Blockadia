package net.thegaminghuskymc.sandboxgame.engine.world.gen;

import java.util.Random;

public class Noise {

    public static Random worldGenRandom = new Random();
    public static NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(worldGenRandom, 5);
    public static Random moistureRandom = new Random();
    public static NoiseGeneratorPerlin perlinMoisture = new NoiseGeneratorPerlin(moistureRandom, 5);

    public static float myNoise(float x, float y, float frequency, float exponent) {
        double e = 1 * perlin.getValue(frequency * 1 * x, 1 * y)
                + 0.5 * perlin.getValue(frequency * 2 * x, 2 * y)
                + 0.25 * perlin.getValue(frequency * 4 * x, 4 * y);
        return (float) Math.pow(e, exponent);
    }

    public static float myMoistureNoise(float x, float y) {
        double e = (1.00 * perlinMoisture.getValue( 1 * x,  1 * y)
                + 0.75 * perlinMoisture.getValue( 2 * x,  2 * y)
                + 0.33 * perlinMoisture.getValue( 4 * x,  4 * y)
                + 0.33 * perlinMoisture.getValue( 8 * x,  8 * y)
                + 0.33 * perlinMoisture.getValue(16 * x, 16 * y)
                + 0.50 * perlinMoisture.getValue(32 * x, 32 * y));
        return (float) e;
    }


}
