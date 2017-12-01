package net.thegaminghuskymc.sandboxgame.engine.world.gen;

import net.thegaminghuskymc.sandboxgame.engine.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class Noise {

    public static Random worldGenRandom = new Random();
    public static NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(worldGenRandom, 5);
    public static Random moistureRandom = new Random();
    public static NoiseGeneratorPerlin perlinMoisture = new NoiseGeneratorPerlin(moistureRandom, 5);

/*
    public static float[][] simplexNoise(int width, int height) {
        float[][] simplexnoise = new float[width][height];
        float frequency = 5.0f / (float) width;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                simplexnoise[x][y] = noise(x * frequency, y * frequency);
                simplexnoise[x][y] = (simplexnoise[x][y] + 1) / 2; //generate values between 0 and 1
            }
        }
        return simplexnoise;
    }*/

//    public static float[][] myNoise(int width, int height, float frequency) {
//        float[][] noise = new float[width][height];
//
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                float nx = (float) (x/width - 0.5), ny = (float) (y/height - 0.5);
//                //noise[x][y] = noise(frequency*nx, frequency*ny)*70;
//                double e = 1 * perlin.getValue(frequency * nx, frequency * ny)/*
//                +  0.5 * perlin.getValue(2 * nx, 2 * ny)
//                + 0.25 * perlin.getValue(4 * nx, 4 * ny)*/;
//                //noise[x][y] = (float) Math.pow(e, 1.24);
//                noise[x][y] = (float) e;
//                //System.out.println(e);
//            }
//        }
//        return noise;
//    }

    public static float myNoise(float x, float y, float frequency, float exponent) {
        double e = 1 * perlin.getValue(frequency * 1 * x, 1 * y)
                + 0.5 * perlin.getValue(frequency * 2 * x, 2 * y)
                + 0.25 * perlin.getValue(frequency * 4 * x, 4 * y);
        //noise[x][y] = (float) Math.pow(e, 1.24);
        return (float) Math.pow(e, exponent);
        //System.out.println(e);
    }

    public static float myMoistureNoise(float x, float y) {
        double e = (1.00 * perlinMoisture.getValue( 1 * x,  1 * y)
                + 0.75 * perlinMoisture.getValue( 2 * x,  2 * y)
                + 0.33 * perlinMoisture.getValue( 4 * x,  4 * y)
                + 0.33 * perlinMoisture.getValue( 8 * x,  8 * y)
                + 0.33 * perlinMoisture.getValue(16 * x, 16 * y)
                + 0.50 * perlinMoisture.getValue(32 * x, 32 * y));
        //noise[x][y] = (float) Math.pow(e, 1.24);
        return (float) e;
        //System.out.println(e);
    }


}
