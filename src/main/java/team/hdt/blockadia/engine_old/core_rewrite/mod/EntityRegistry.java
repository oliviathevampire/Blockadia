package team.hdt.blockadia.engine.core_rewrite.mod;

import com.google.common.collect.Maps;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;
import team.hdt.blockadia.engine.core_rewrite.game.entity.Entity;

import java.util.Map;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Has the capability to register an entity to the game.
 * 
 * @author Ocelot5836
 */
public class EntityRegistry {

	private static final Map<String, Class<? extends Entity>> REGISTRY = Maps.<String, Class<? extends Entity>>newHashMap();

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
	public static boolean register(Identifier registryName, Class<? extends Entity> clazz) {
		if (REGISTRY.containsKey(registryName.toString())) {
			throw new RuntimeException("Attempted to register an entity over another. OLD: " + registryName + ", NEW: " + registryName);
		} else {
			REGISTRY.put(registryName.toString(), clazz);
			return true;
		}
	}

	/**
	 * Unregisters an entity by using their class.
	 * 
	 * @param registryName
	 *            The registry name of the entity to be unregistered.
	 * 
	 * @return Whether or not the registration was a success.
	 */
	public static boolean unregister(Identifier registryName) {
		if (REGISTRY.containsKey(registryName.toString())) {
			REGISTRY.remove(registryName.toString());
		}
		Blockadia.logger().error("Entity with registry name " + registryName + " could not be found in the registry. Removal canceled.");
		return false;
	}

	/**
	 * Checks the registry for the entity's registry name.
	 * 
	 * @param entity
	 *            The entity to validate
	 * @return Whether or not the entity is actually valid
	 */
	public static boolean validate(Entity entity) {
		for (String registryName : REGISTRY.keySet()) {
			if (REGISTRY.get(registryName) == entity.getClass()) {
				return true;
			}
		}
		return false;
	}
}
