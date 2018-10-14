package team.hdt.blockadia.engine.core_rewrite.game.world.map;

import team.hdt.blockadia.engine.core_rewrite.util.ISerializable;
import team.hdt.blockadia.engine.core_rewrite.util.data.ByteDataContainer;

import java.util.UUID;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * This is a single 100x100 tile area. Chunks are only used for saving/loading data from file.
 * 
 * @author Ocelot5836
 */
public class Chunk implements ISerializable<ByteDataContainer> {
	
	private boolean offScreen;
	private ByteDataContainer tileData;
	private UUID chunkId;
	private int gridX;
	private int gridY;

	/**
	 * @param id
	 *            The id that will be used to identify the chunk
	 * @param gridX
	 *            The x position on the chunk grid
	 * @param gridY
	 *            The y position on the chunk grid
	 */
	public Chunk(UUID id, int gridX, int gridY) {
		this.tileData = new ByteDataContainer();
		this.chunkId = id;
		this.gridX = gridX;
		this.gridY = gridY;
	}

	/**
	 * @return The tile data compacted into a byte data container
	 */
	public ByteDataContainer getTileData() {
		return tileData;
	}

	/**
	 * @return The id of this chunk. Used to identify the file the tiles are saved to
	 */
	public UUID getId() {
		return chunkId;
	}

	/**
	 * @return The x position on the chunk grid
	 */
	public int getGridX() {
		return gridX;
	}

	/**
	 * @return The y position on the chunk grid
	 */
	public int getGridY() {
		return gridY;
	}
	
	public boolean isOffScreen() {
		return offScreen;
	}
	
	public void setOffScreen(boolean offScreen) {
		this.offScreen = offScreen;
	}

	@Override
	public ByteDataContainer serialize() {
		ByteDataContainer container = new ByteDataContainer();
		container.setUUID("cn", chunkId);
		return container;
	}

	@Override
	public void deserialize(ByteDataContainer data) {
		this.chunkId = data.getUUID("cn");
	}
}