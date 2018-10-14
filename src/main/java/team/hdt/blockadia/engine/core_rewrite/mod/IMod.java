package team.hdt.blockadia.engine.core_rewrite.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a dummy class, for testing purposes. Do not touch.
 * 
 * @author HuskyTheArtist
 *
 */
public interface IMod {
	
	/**
	 * @return The version for the mod. Must not contain any letters. Examples: 0.1.0, 1.0.0, 1.2.0
	 */
	public String getModVersion();
	
	/**
	 * @return The name of the mod. Please note that the name here should be unique. Mods with the same name will crash the game deliberately!
	 */
	public String getModName();
	
	/**
	 * @return The version of Zerra this mod was made for. If the version for this mod does not equal the game version, the game will deliberately crash!
	 */
	public String getGameVersionForMod();

	/**
	 * Pre Initialization. This is a method that executes before Blockadia initializes. Handle with care.
	 */
	public void preInit();
	
	/**
	 * Initialization. Register tiles, entities, items, and so on. Make sure you load in a safe order. 
	 * Some objects depend on other objects to be registered before they are! 
	 * 
	 * This method is called after Blockadia itself has initialized the base game, so use this for most of your changes.
	 */
	public void init();
	
	/**
	 * Post Initialization. This happens roughly after every other mod has initialized. If you are checking for other mods,
	 * this is the ideal place to do so.
	 */
	public void postInit();
	
	/**
	 * @return A logger that uses this mod's mod id
	 */
	default Logger logger() {
		return LogManager.getLogger(this.getModName());
	}

}