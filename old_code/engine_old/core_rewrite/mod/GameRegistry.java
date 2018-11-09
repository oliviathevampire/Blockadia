package team.hdt.blockadia.old_engine_code_1.core_rewrite.mod;

import team.hdt.blockadia.old_engine_code_1.core_rewrite.util.Identifier;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.entity.Entity;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.item.Item;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.state.GameState;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.state.GameStateManager;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.world.tile.Tile;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.handler.Items;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.handler.Tiles;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.keybind.Keybind;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.keybind.KeybindManager;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * This has all the methods that would be needed to register anything and add it to the game. This is mainly for modders to make it easier to register things.
 * 
 * @author Ocelot5836
 */
@SuppressWarnings("deprecation")
public class GameRegistry {

	/**
	 * Registers an item. Will throw an exception if the registry name for the item is null.
	 * 
	 * @param item
	 *            The item to register
	 */
	public static void registerItem(Item item) {
		Items.registerItem(item);
	}

	/**
	 * Registers a tile. Will throw an exception if the registry name for the tile is null.
	 * 
	 * @param tile
	 *            The tile to register
	 */
	public static void registerTile(Tile tile) {
		Tiles.registerTile(tile);
	}

	/**
	 * Registers a state to the game state manager.
	 * 
	 * @param clazz
	 *            The state to register
	 */
	public static int registerGameState(Class<? extends GameState> clazz) {
		return GameStateManager.registerState(clazz);
	}

	/**
	 * Registers a keybind.
	 * 
	 * @param key
	 *            The key that this will be bound to
	 * @return The keybind created
	 */
	public static Keybind registerKeybind(int key) {
		return KeybindManager.registerKeyBind(key);
	}

	/**
	 * Registers an entity by using their class.
	 * 
	 * @param registryName
	 *            The name the entity is going to be associated with
	 * @param clazz
	 *            The class of the entity to be registered.
	 * 
	 * @return Whether or not the registration was a success.
	 */
	public static boolean registerEntity(Identifier registryName, Class<? extends Entity> clazz) {
		return EntityRegistry.register(registryName, clazz);
	}
}