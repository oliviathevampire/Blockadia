package team.hdt.blockadia.engine.core_rewrite.handler;

import com.google.common.collect.Maps;
import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.game.item.BasicItem;
import team.hdt.blockadia.engine.core_rewrite.game.item.Item;
import team.hdt.blockadia.engine.core_rewrite.mod.GameRegistry;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class Items {

	public static final Map<String, Item> REGISTRY = Maps.<String, Item>newHashMap();

	public static final Item TEST = new BasicItem(new Identifier("test"), "test", new Vector2f(0, 0));
	public static final Item SAPPHIRE = new BasicItem(new Identifier("sapphire"), "sapphire", new Vector2f(0, 1));
	public static final Item RUBY = new BasicItem(new Identifier("ruby"), "ruby", new Vector2f(1, 1));
	public static final Item EMERALD = new BasicItem(new Identifier("emerald"), "emerald", new Vector2f(2, 1));
	public static final Item DIAMOND = new BasicItem(new Identifier("diamond"), "diamond", new Vector2f(3, 1));

	/**
	 * Registers an item.
	 * 
	 * @param item
	 *            The item to register
	 * @deprecated This should not be used by modders
	 */
	public static void registerItem(Item item) {
		if (REGISTRY.containsKey(item.getRegistryName().toString())) {
			throw new RuntimeException("Item \'" + item + "\' attempted to override item \'" + REGISTRY.get(item.getRegistryName().toString()) + "\'!");
		}
		REGISTRY.put(item.getRegistryName().toString(), item);
	}

	/**
	 * Gets the item type based on the registry name.
	 * 
	 * @param registryName
	 *            The registry name used to search the registry with.
	 * 
	 * @return The item retrieved from the registry or null if there was no mapping for it
	 */
	@Nullable
	public static Item byName(String registryName) {
		return REGISTRY.get(registryName);
	}

	/**
	 * @return The key set for the registry map
	 */
	public static Set<String> keySet() {
		return REGISTRY.keySet();
	}

	/**
	 * Registers all of the default items.
	 */
	public static void registerItems() {
		GameRegistry.registerItem(TEST);
		GameRegistry.registerItem(SAPPHIRE);
		GameRegistry.registerItem(RUBY);
		GameRegistry.registerItem(EMERALD);
		GameRegistry.registerItem(DIAMOND);
	}
}