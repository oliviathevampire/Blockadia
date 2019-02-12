package team.hdt.blockadia.engine.core_rewrite.game.world.tile;

import team.hdt.blockadia.engine.core_rewrite.game.world.map.EnumBiome;
import team.hdt.blockadia.engine.core_rewrite.handler.Tiles;
import team.hdt.blockadia.engine.core_rewrite.util.ISerializable;
import team.hdt.blockadia.engine.core_rewrite.util.data.ByteDataContainer;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A single tile in a world. It stores data that a static tile instance simple could not.
 * 
 * @author Ocelot5836
 */
public class TileEntry implements ISerializable<ByteDataContainer> {

	private Tile tile;
	private EnumBiome biome;
	private int layer;
	private float lastX;
	private float lastY;
	private float x;
	private float y;
	private boolean removed;

	public TileEntry(Tile tile, EnumBiome biome, int layer, float x, float y) {
		this.tile = tile;
		this.biome = biome;
		this.lastX = x;
		this.lastY = y;
		this.x = x;
		this.y = y;
		this.removed = false;
	}

	/**
	 * @return The tile in this tile entry.
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @return The biome of this tile entry.
	 */
	public EnumBiome getBiome() {
		return biome;
	}

	/**
	 * @return The layer of this tile entry.
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @return The x position of this tile entry.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return The y position of this tile entry.
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return The last x position of this tile entry.
	 */
	public float getLastX() {
		return lastX;
	}

	/**
	 * @return The last y position of this tile entry.
	 */
	public float geLasttY() {
		return lastY;
	}

	/**
	 * @return Whether or not this tile entry is removed. This is used to determine if this tile should be removed from the tile list.
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * Sets the x position for the tile in this tile entry.
	 * 
	 * @param x
	 *            The x position to set the tile at.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets the y position for the tile in this tile entry.
	 * 
	 * @param y
	 *            The y position to set the tile at.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Sets whether or not this tile is removed. This is used to determine if this tile should be removed from the tile list.
	 * 
	 * @param removed
	 *            Whether or not the tile is removed.
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	/**
	 * Writes tile entry data to a byte stream, to be stored in chunk data as binary.
	 */
	@Override
	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		container.setString("rn", this.tile.getRegistryName().toString());
		container.setByte("b", (byte) this.biome.ordinal());
		return container;
	}

	/**
	 * Reads tile entry data from chunk files, to be loaded into the world once more.
	 */
	@Override
	public void deserialize(ByteDataContainer data) {
		this.tile = Tiles.byName(data.getString("rn"));
		this.biome = EnumBiome.values()[data.getByte("b")];
	}

	/**
	 * Retrieves a tile entry based on a tag.s
	 * 
	 * @param x
	 *            The x position of the tile.
	 * 
	 * @param y
	 *            The y position of the tile.
	 * 
	 * @param layer
	 *            The number of layers the tile has.
	 * 
	 * @param data
	 *            The serialized data to deserialize. This gets the rest of the data a tile has, which includes biome type, tile type, etc.
	 * @return
	 */
	public static TileEntry fromTag(float x, float y, int layer, ByteDataContainer data) {
		TileEntry tile = new TileEntry(null, null, layer, x, y);
		tile.deserialize(data);
		return tile;
	}
}