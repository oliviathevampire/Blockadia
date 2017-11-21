package net.thegaminghuskymc.sandboxgame.engine;

import java.util.Random;

public class HeightsGenerator {

    private static final float AMPLITUDE = 70f;

    private Random random = new Random();
    private int seed;

    public HeightsGenerator() {
        this.seed = random.nextInt(1000000000);
    }

    public float generateHeight(int x, int y) {
        return 1;
    }

}
