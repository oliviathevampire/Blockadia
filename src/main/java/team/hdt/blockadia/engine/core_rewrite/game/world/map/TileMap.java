package team.hdt.blockadia.engine.core_rewrite.game.world.map;

import org.joml.Vector3f;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;
import team.hdt.blockadia.engine.core_rewrite.Display;
import team.hdt.blockadia.engine.core_rewrite.game.world.tile.Tile;
import team.hdt.blockadia.engine.core_rewrite.game.world.tile.TileEntry;
import team.hdt.blockadia.engine.core_rewrite.gfx.FrustumCullingFilter;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.handler.Tiles;
import team.hdt.blockadia.engine.core_rewrite.object.ICamera;
import team.hdt.blockadia.engine.core_rewrite.util.Maths;
import team.hdt.blockadia.engine.core_rewrite.util.data.ByteDataContainer;

import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Handles all the tiles in a level.
 * 
 * @author Ocelot5836, Hypeirochus
 */
public class TileMap {

	public static final int CHUNK_SIZE = 100;

	private File worldFolder;
	private WorldGenerationManager worldGenerator;
	private FrustumCullingFilter filter;

	private ByteDataContainer chunksList;
	private List<Chunk> chunks;
	private List<TileEntry> tiles;

	private float width;
	private float height;

	public TileMap() {
		worldGenerator = new WorldGenerationManager(this);
		filter = new FrustumCullingFilter();
		chunksList = new ByteDataContainer();
		chunks = new ArrayList<>();
		tiles = new ArrayList<>();
		width = Display.getWidth() / MasterRenderer.scale / 16;
		height = Display.getHeight() / MasterRenderer.scale / 16;
	}

	/**
	 * Updates the world tiles. Manages whether or not tiles should be loaded/unloaded based on their location on/off screen.
	 */
	public void update() {
		ICamera camera = Blockadia.getInstance().getCamera();
		filter.updateFrustum(MasterRenderer.getProjectionMatrix(), Maths.createViewMatrix(camera));
		filter.filterTiles(tiles);
		filter.filterChunks(chunks);

		for (int i = 0; i < chunks.size(); i++) {
			Chunk chunk = chunks.get(i);
			if (chunk.isOffScreen()) {
				try {
					this.saveChunkToFile(chunk);
				} catch (IOException e) {
					e.printStackTrace();
				}
				chunks.remove(i);
				i--;
			}
		}

		boolean hasRemovedTiles = false;
		for (int i = 0; i < tiles.size(); i++) {
			TileEntry tile = tiles.get(i);
			tile.getTile().update();
			if (tile.isRemoved()) {
				tiles.remove(i);
				i--;
				tile.getTile().onTileDestroyed(this, tile.getX(), tile.getY());
				this.getChunk(tile.getX(), tile.getY()).getTileData().setTag(tile.getX() + "," + tile.getY() + "," + tile.getLayer(), tile.serialize());
				hasRemovedTiles = true;
			}
		}

		if (hasRemovedTiles) {
			Vector3f direction = camera.getDirection();
			float x = camera.getPosition().x;
			float y = camera.getPosition().y;
			if (direction.x < 0) {
				for (int tileX = 2; tileX < 4; tileX++) {
					for (int tileY = -2; tileY < height + 1; tileY++) {
						int xPos = (int) (tileX + (Math.ceil(x - 64) / 16));
						int yPos = (int) (y / 16 + tileY);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.worldGenerator.generateTile(xPos * 16, yPos * 16);
					}
				}
			}

			if (direction.x > 0) {
				for (int tileX = 3; tileX < 4; tileX++) {
					for (int tileY = -1; tileY < height + 1; tileY++) {
						int xPos = (int) (tileX + width + (Math.ceil(x - 32) / 16));
						int yPos = (int) (y / 16 + tileY);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.worldGenerator.generateTile(xPos * 16, yPos * 16);
					}
				}
			}

			if (direction.y < 0) {
				for (int tileX = -1; tileX < width + 1; tileX++) {
					for (int tileY = -2; tileY < 1; tileY++) {
						int xPos = (int) (x / 16 + tileX);
						int yPos = (int) Math.ceil(tileY + y / 16);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.worldGenerator.generateTile(xPos * 16, yPos * 16);
					}
				}
			}

			if (direction.y > 0) {
				for (int tileX = -1; tileX < width + 2; tileX++) {
					for (int tileY = 1; tileY < 2; tileY++) {
						int xPos = (int) (x / 16 + tileX);
						int yPos = (int) Math.floor(tileY + height + y / 16);
						if (getTile(xPos * 16, yPos * 16) == null)
							this.worldGenerator.generateTile(xPos * 16, yPos * 16);
					}
				}
			}
		}
	}

	/**
	 * Updates the rendering for all tiles on screen.
	 * 
	 * @param renderer
	 *            The master renderer used to update tile rendering.
	 */
	public void render(MasterRenderer renderer) {
		for (int i = 0; i < tiles.size(); i++) {
			renderer.renderTile(tiles.get(i));
		}
	}

	/**
	 * Generates the initial tiles.
	 */
	public void generate() {
		for (int x = -1; x < width + 2; x++) {
			for (int y = -1; y < height + 2; y++) {
				if (getTile(x * 16, y * 16) == null)
					this.worldGenerator.generateTile(x * 16, y * 16);
			}
		}
	}

	/**
	 * Saves the world (how heroic sounding).
	 * 
	 * @param saveFolder
	 *            The location to save the world to.
	 * 
	 * @throws IOException
	 *             Potentially thrown due to world files not being found.
	 */
	public void save(File saveFolder, String worldName) throws IOException {
		/** The folder the world is actually in */
		worldFolder = new File(saveFolder, worldName);
		/** The file the chunk files are able to be identified. ex. (0,0=741261b0-b0e9-4466-9df5-0c7f76101925) */
		File tileDataFile = new File(worldFolder, "tiles.bit");
		/** The file that defines what id corresponds to each tile */
		File tileIndexFile = new File(worldFolder, "index.bit");
		/** Creates the world folder if it does not yet exist */
		if (!worldFolder.exists()) {
			worldFolder.mkdirs();
		}

		tileDataFile.createNewFile();
		tileIndexFile.createNewFile();

		for (int i = 0; i < chunks.size(); i++) {
			this.saveChunkToFile(this.chunks.get(i));
		}
		/** Writes the chunk identifiers to file */
		DataOutputStream tileStream = new DataOutputStream(new FileOutputStream(tileDataFile));
		chunksList.write(tileStream);

		ByteDataContainer tileIndex = new ByteDataContainer();
		tileIndex.setLong("randomSeed", worldGenerator.getRandomSeed());
		tileIndex.setInt("seed", worldGenerator.getSeed());

		DataOutputStream tileIndexStream = new DataOutputStream(new FileOutputStream(tileIndexFile));
		tileIndex.write(tileIndexStream);
	}

	/**
	 * Loads the world.
	 * 
	 * @param saveFolder
	 *            The save folder to load the world from.
	 * 
	 * @param worldName
	 *            The name of the world to load.
	 * 
	 * @throws IOException
	 *             Potentially thrown due to world files not being found.
	 */
	public void load(File saveFolder, String worldName) throws IOException {
		worldFolder = new File(saveFolder, worldName);
		File tileDataFile = new File(worldFolder, "tiles.bit");
		File tileIndexFile = new File(worldFolder, "index.bit");

		/** Attempts to load the data file to know what files to load on the fly */
		if (tileDataFile.exists()) {
			DataInputStream tileStream = new DataInputStream(new FileInputStream(tileDataFile));
			chunksList.read(tileStream);
		}

		if (tileIndexFile.exists()) {
			DataInputStream tileIndexStream = new DataInputStream(new FileInputStream(tileIndexFile));
			ByteDataContainer tileIndex = new ByteDataContainer();
			tileIndex.read(tileIndexStream);
			worldGenerator.setSeeds(tileIndex.getLong("randomSeed"), tileIndex.getInt("seed"));
		}
	}

	/**
	 * Gets a tile at the specified position.
	 * 
	 * @param x
	 *            The x position of the tile.
	 * @param y
	 *            The y position of the tile.
	 * 
	 * @return The tile at the position.
	 */
	@Nullable
	public Tile getTile(int x, int y) {
		for (int i = 0; i < tiles.size(); i++) {
			TileEntry tile = tiles.get(i);
			if ((int) Math.floor(tile.getX()) == x && (int) Math.floor(tile.getY()) == y) {
				return tile.getTile();
			}
			if (this.getChunk(x, y).getTileData().hasKey(x + "," + y, 10)) {
				return Tiles.byName(this.getChunk(x, y).getTileData().getByteContainer(x + "," + y).getString("rn"));
			}
		}
		return null;
	}

	/**
	 * Places a tile by adding it to the tile list.
	 * 
	 * @param tile
	 *            The tile to add
	 * @param biome
	 *            The biome the tile is in
	 * @param layer
	 *            The layer of the tile
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 */
	protected void addTile(Tile tile, EnumBiome biome, int layer, int x, int y) {
		TileEntry entry = null;
		if (this.getChunk(x, y).getTileData().hasKey(x + "," + y, 10)) {
			entry = TileEntry.fromTag(x, y, layer, this.getChunk(x, y).getTileData().getByteContainer(x + "," + y + "," + layer));
		}

		if (entry == null) {
			entry = new TileEntry(tile, biome, layer, x, y);
		}
		entry.getTile().onTilePlaced(this, x, y);
		tiles.add(entry);
	}

	/**
	 * Loads a chunk from file if it has been generated or creates a new chunk if there is no file found.
	 * 
	 * @param x
	 *            The x position to get the chunk at
	 * @param y
	 *            The y position to get the chunk at
	 * 
	 * @return The chunk that was either loaded or created
	 */
	protected Chunk getChunk(float x, float y) {
		int gridX = (int) (x / 16 / CHUNK_SIZE);
		int gridY = (int) (y / 16 / CHUNK_SIZE);
		for (int i = 0; i < chunks.size(); i++) {
			Chunk chunk = chunks.get(i);
			if (chunk.getGridX() == gridX && chunk.getGridY() == gridY)
				return chunk;
		}
		Chunk chunk = null;
		try {
			chunk = this.tryLoadingChunk(gridX, gridY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (chunk == null) {
			Blockadia.logger().info("Created new chunk at " + gridX + "," + gridY);
			chunk = new Chunk(UUID.randomUUID(), gridX, gridY);
		}
		chunks.add(chunk);
		return chunk;
	}

	/**
	 * Attempts to load a single chunk from file.
	 * 
	 * @param gridX
	 *            The grid x position of tdhe chunk
	 * @param gridY
	 *            The grid y position of the chunk
	 * 
	 * @return The chunk that was loaded or null if the chunk could not be loaded
	 * 
	 * @throws IOException
	 *             If anything goes wrong when trying to load the chunk file
	 */
	@Nullable
	private Chunk tryLoadingChunk(int gridX, int gridY) throws IOException {
		ByteDataContainer container = chunksList.getByteContainer(gridX + "," + gridY);
		if (container != null) {
			UUID chunkId = container.getUUID("cn");
			if (chunkId != null) {
				File chunkFile = new File(worldFolder, "chunks/" + chunkId);
				Blockadia.logger().info("Attempting to load chunk at x: " + gridX + " y: " + gridY);
				if (chunkFile.exists()) {
					Chunk chunk = new Chunk(chunkId, gridX, gridY);
					DataInputStream stream = new DataInputStream(new FileInputStream(chunkFile));
					chunk.getTileData().read(stream);
					Blockadia.logger().info("Chunk at x: " + gridX + " y: " + gridY + " was successfully loaded");
					return chunk;
				}
				Blockadia.logger().error("Unable to find chunk file!");
			}
		}
		return null;
	}

	/**
	 * Saves a chunk to file.
	 * 
	 * @param chunk
	 *            The chunk to save
	 * @throws IOException
	 *             If the chunk file could not be created or there was an issue with the byte data
	 */
	private void saveChunkToFile(Chunk chunk) throws IOException {
		Blockadia.logger().info("Saving chunk at x: " + chunk.getGridX() + " y: " + chunk.getGridY() + " to file.");

		UUID chunkName = chunk.getId();

		/** The chunk data is used to specify chunk specific data */
		ByteDataContainer chunkData = new ByteDataContainer();
		chunkData.setUUID("cn", chunkName);

		File file = new File(worldFolder, "chunks/" + chunkName.toString());
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		/** Sets the chunk data to the chunk pos */
		chunksList.setTag(chunk.getGridX() + "," + chunk.getGridY(), chunkData);

		DataOutputStream tileStream = new DataOutputStream(new FileOutputStream(file));
		chunk.getTileData().write(tileStream);
	}
}
