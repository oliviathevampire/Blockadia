package team.hdt.blockadia.game_engine.common.world.misc;

import team.hdt.blockadia.game_engine.common.util.math.Maths;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;

import java.util.Random;

public class PerlinNoise {

    public static final float AMPLITUDE = 15;
    private static final float ROUGHNESS = 0.43f;//0.43
    private static final float OCTAVES = 5;
    private static final float EDGE = 0.98f;
    private static final float TRANSITION = 0.5f;
    private final float smoothness;
    private final float max;
    private final float halfMaxSquared;
    private final Vectors2f center;
    private float edgeHeight = -1f;
    private boolean roundWorld = false;

    private int seed;

    public PerlinNoise(int seed, float smoothness, float size, float edgeHeight) {
        this.seed = seed;
        this.edgeHeight = edgeHeight;
        this.smoothness = smoothness;
        this.max = size;
        this.halfMaxSquared = (max * 0.5f) * (max * 0.5f);
        this.center = new Vectors2f(max / 2f, max / 2f);
    }

    public PerlinNoise(float smoothness, float size, float edgeHeight) {
        this.seed = Maths.RANDOM.nextInt(1000000000);
        this.smoothness = smoothness;
        this.edgeHeight = edgeHeight;
        this.max = size;
        this.halfMaxSquared = (max * 0.5f) * (max * 0.5f);
        this.center = new Vectors2f(max / 2f, max / 2f);
    }

    public int getSeed() {
        return seed;
    }

    public float getPerlinNoise(float x, float y) {
        float total = 0;
        float d = (float) Math.pow(2, OCTAVES - 1);
        for (int i = 0; i < OCTAVES; i++) {
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
            total += getInterpolatedNoise(x * smoothness * freq, y * smoothness * freq) * amp;
        }
        float edgeFactor = roundWorld ? getEdgeFactor(x, y) : getEdgeFactorSquare(x, y);
        return total * edgeFactor + (1 - edgeFactor) * edgeHeight;
    }

    private float getSmoothNoise(int x, int y) {
        float corners = (getNoise(x - 1, y - 1) + getNoise(x + 1, y - 1) + getNoise(x - 1, y + 1)
                + getNoise(x + 1, y + 1)) / 16f;
        float sides = (getNoise(x - 1, y) + getNoise(x + 1, y) + getNoise(x, y - 1) + getNoise(x, y + 1)) / 8f;
        float center = getNoise(x, y) / 4f;
        return corners + sides + center;
    }

    private float getNoise(int x, int y) {
        return new Random(x * 49632 + y * 325176 + seed).nextFloat() * 2f - 1f;
    }

    private float getInterpolatedNoise(float x, float y) {
        int intX = (int) x;
        float fracX = x - intX;
        int intY = (int) y;
        float fracY = y - intY;

        float v1 = getSmoothNoise(intX, intY);
        float v2 = getSmoothNoise(intX + 1, intY);
        float v3 = getSmoothNoise(intX, intY + 1);
        float v4 = getSmoothNoise(intX + 1, intY + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracY);
    }

    private float interpolate(float a, float b, float blend) {
        double theta = blend * Math.PI;
        float f = (float) ((1f - Math.cos(theta)) * 0.5f);
        return a * (1 - f) + b * f;
    }

    private float getEdgeFactor(float x, float y) {

        Vectors2f vector = new Vectors2f(x, y);
        float disSquared = Vectors2f.sub(vector, center, vector).lengthSquared();
        return 1 - Maths.smoothStep(EDGE - TRANSITION, EDGE, disSquared / halfMaxSquared);

    }

    private float getEdgeFactorSquare(float x, float y) {

        float nearEdge = max * 0.1f;
        float farEdge = max - nearEdge;

        float xFactorNear = Maths.clamp(x / nearEdge, 0f, 1f);
        float xFactor = Maths.clamp(1f - (x - farEdge) / nearEdge, 0, xFactorNear);
        float yFactorNear = Maths.clamp(y / nearEdge, 0f, xFactor);
        return Maths.clamp(1f - (y - farEdge) / nearEdge, 0, yFactorNear);
    }

}
