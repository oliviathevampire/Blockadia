package team.hdt.blockadia.engine.core_rewrite.game.world.map;

import team.hdt.blockadia.engine.core_rewrite.game.world.tile.Tile;
import team.hdt.blockadia.engine.core_rewrite.handler.Tiles;

public enum EnumBiome {
	OCEAN(0, Tiles.WATER), BEACH(1, Tiles.SAND), DESERT(4, Tiles.SAND), FOREST(2, Tiles.GRASS), MOUNTAIN(3, Tiles.STONE), SNOW(5, Tiles.SNOW);

	private int id;
	private Tile tile;

	private EnumBiome(int id, Tile tile) {
		this.id = id;
		this.tile = tile;
	}

	/**
	 * @return The ID of the biome type.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The base tile of the biome.
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Gets the biome type by ID.
	 * 
	 * @param id
	 *            The ID of the biome type that is to be retrieved.
	 * 
	 * @return The biome type from the ID.
	 */
	public static EnumBiome byId(int id) {
		return EnumBiome.values()[id % EnumBiome.values().length];
	}
}