package team.hdt.blockadia.engine.core_rewrite.handler;

import com.google.common.collect.Maps;
import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.game.world.tile.Tile;
import team.hdt.blockadia.engine.core_rewrite.game.world.tile.TileBase;
import team.hdt.blockadia.engine.core_rewrite.game.world.tile.TileLava;
import team.hdt.blockadia.engine.core_rewrite.game.world.tile.TileLayeredBase;
import team.hdt.blockadia.engine.core_rewrite.mod.GameRegistry;

import java.util.Map;
import java.util.Set;

public class Tiles {

	private static final Map<String, Tile> REGISTRY = Maps.<String, Tile>newHashMap();

	public static final Tile VOID = new TileBase(new Identifier("void"), "void", new Vector2f(0, 0));
	public static final Tile GRASS = new TileBase(new Identifier("grass"), "grass", new Vector2f(1, 0));
	public static final Tile STONE = new TileBase(new Identifier("stone"), "stone", new Vector2f(2, 0));
	public static final Tile COBBLESTONE = new TileBase(new Identifier("cobblestone"), "cobblestone", new Vector2f(3, 0));
	public static final Tile SAND = new TileBase(new Identifier("sand"), "sand", new Vector2f(4, 0));
	public static final Tile LAVA = new TileLava(new Identifier("lava"), "lava", new Vector2f(2, 1));
	public static final Tile WATER = new TileLayeredBase(new Identifier("water"), "water", new Vector2f(4, 0), new Vector2f(1, 1));
	public static final Tile SNOW = new TileBase(new Identifier("snow"), "snow", new Vector2f(6, 0));

	public static final Tile PLANKS = new TileBase(new Identifier("planks"), "planks", new Vector2f(5, 0));

	public static final Tile OBSIDIAN = new TileBase(new Identifier("obsidian"), "obsidian", new Vector2f(3, 1));
	public static final Tile GOLD_ORE = new TileBase(new Identifier("gold_ore"), "gold_ore", new Vector2f(7, 0));

	public static final Tile BOULDER = new TileBase(new Identifier("boulder"), "boulder", new Vector2f(0, 1));
	public static final Tile FOREST_BUSH = new TileBase(new Identifier("forest_bush"), "forest_bush", new Vector2f(0, 2));
	public static final Tile DEAD_BUSH = new TileBase(new Identifier("dead_bush"), "dead_bush", new Vector2f(1, 2));
	public static final Tile SNOW_BUSH = new TileBase(new Identifier("snow_bush"), "snow_bush", new Vector2f(2, 2));

	/**
	 * Gets the tile type based on the registry name.
	 * 
	 * @param registryName
	 *            The registry name used to search the registry with.
	 * 
	 * @return The tile retrieved from the registry
	 */
	public static Tile byName(String registryName) {
		Tile tile = REGISTRY.get(registryName);
		return tile != null ? tile : VOID;
	}

	/**
	 * @return The key set for the registry map
	 */
	public static Set<String> keySet() {
		return REGISTRY.keySet();
	}	
	
	/**
	 * Registers a tile.
	 * 
	 * @param tile
	 *            The tile to register
	 * @deprecated This should not be used by modders
	 */
	public static void registerTile(Tile tile) {
		if (!REGISTRY.containsKey(tile.getRegistryName().toString())) {
			REGISTRY.put(tile.getRegistryName().toString(), tile);
		} else {
			throw new RuntimeException("Attempted to register a tile over another. OLD: " + REGISTRY.get(tile.getRegistryName().toString()).getLocalizedName() + ", NEW: " + tile.getLocalizedName());
		}
	}
	
	/**
	 * Registers all the default tiles.
	 */
	public static void registerTiles() {
		GameRegistry.registerTile(VOID);
		GameRegistry.registerTile(GRASS);
		GameRegistry.registerTile(STONE);
		GameRegistry.registerTile(COBBLESTONE);
		GameRegistry.registerTile(SAND);
		GameRegistry.registerTile(LAVA);
		GameRegistry.registerTile(WATER);
		GameRegistry.registerTile(SNOW);
		
		GameRegistry.registerTile(OBSIDIAN);
		GameRegistry.registerTile(GOLD_ORE);
		
		GameRegistry.registerTile(BOULDER);
		GameRegistry.registerTile(FOREST_BUSH);
		GameRegistry.registerTile(DEAD_BUSH);
		GameRegistry.registerTile(SNOW_BUSH);
	}
}