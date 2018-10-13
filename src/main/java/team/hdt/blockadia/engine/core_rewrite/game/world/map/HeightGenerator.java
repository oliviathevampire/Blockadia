package team.hdt.blockadia.engine.core_rewrite.game.world.map;

import team.hdt.blockadia.engine.core_rewrite.util.Maths;

import java.util.Random;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * This class uses Perlin Noise to generate random, fairly natural looking terrain.
 * 
 * @author Ocelot5836
 */
public class HeightGenerator {

	private Random random;
	private long randomSeed;
	private int seed;

	public HeightGenerator() {
		this.randomSeed = System.nanoTime();
		this.random = new Random(this.randomSeed);
		this.seed = random.nextInt(1000000000);
	}

	public HeightGenerator(long randomSeed, int seed) {
		this.random = new Random(randomSeed);
		this.seed = seed;
	}

	/**
	 * This generates height based on the values passed in.
	 * 
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @param amplitude
	 *            The maximum distance between points
	 * @param octaves
	 *            The number of times the terrain is smoothed out
	 * @param roughness
	 *            how much detail is lost per octave
	 * @return The height at that tile
	 */
	public float generateHeight(float x, float y, float amplitude, int octaves, float roughness) {
		float total = 0;
		float d = (float) Math.pow(2, octaves - 1);
		for (int i = 0; i < octaves; i++) {
			float freq = (float) (Math.pow(2, i) / d);
			float amp = (float) Math.pow(roughness, i) * amplitude;
			total += this.getInterpolatedNoise(x * freq, y * freq) * amp;
		}
		return total;
	}

	/**
	 * This returns an interpolated, interpolated noise based on all the interpolated noises around this tile.
	 * 
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @return The interpolated, interpolated noise
	 */
	private float getInterpolatedNoise(float x, float y) {
		int intX = (int) x;
		int intY = (int) y;
		float fracX = x - intX;
		float fracY = y - intY;

		float v1 = getSmoothNoise(intX, intY);
		float v2 = getSmoothNoise(intX + 1, intY);
		float v3 = getSmoothNoise(intX, intY + 1);
		float v4 = getSmoothNoise(intX + 1, intY + 1);

		float i1 = Maths.interpolate(v1, v2, fracX);
		float i2 = Maths.interpolate(v3, v4, fracX);
		return Maths.interpolate(i1, i2, fracY);
	}

	/**
	 * This interpolates the noise of each tile based on the tiles around it. With the tiles closer having more of an effect on the smoothness.
	 * 
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @return An interpolated version of noise
	 */
	private float getSmoothNoise(int x, int y) {
		float corners = (getNoise(x - 1, y - 1) + getNoise(x + 1, y - 1) + getNoise(x - 1, y + 1) + getNoise(x + 1, y + 1)) / 16f;
		float sides = (getNoise(x - 1, y) + getNoise(x + 1, y) + getNoise(x, y - 1) + getNoise(x, y + 1)) / 8f;
		float center = getNoise(x, y) / 4f;
		return corners + sides + center;
	}

	/**
	 * This generates a random number, different from the numbers around it, but also the same for the same coord. Ex. (x=0, y=0, noise=-0.47f, x=1, y=0, noise=0.27)
	 * 
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @return a value between -1 and 1
	 */
	private float getNoise(int x, int y) {
		random.setSeed(x * 49632 + y * 32517 + seed);
		return random.nextFloat() * 2f - 1f;
	}

	/**
	 * @return The seed for the actual world generation
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * @return The world's random object seed
	 */
	public long getRandomSeed() {
		return randomSeed;
	}
}