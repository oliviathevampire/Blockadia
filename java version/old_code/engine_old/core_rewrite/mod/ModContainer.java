package team.hdt.blockadia.engine.core_rewrite.mod;

import team.hdt.blockadia.engine.core_rewrite.Blockadia;

/**
 * This is an example main mod class that other programmers can look at. Hopefully everything is obvious as to what it is.
 * This class is not called directly by the game at all. Which is good. All mods are called through ModService.java, where
 * that class checks for classes that implement IMod. YOUR MOD MUST IMPLEMENT BLOCKADIA'S IMOD INTERFACE TO WORK!
 *<br/><br/>
 * Once you make a mod class similar to this, you must register it for ModService to pick it up. All you have to do is simply
 * make a META-INF folder under your resources folder. Once you do that, you make a folder called services under that folder.
 * <br/><br/>
 * Under services, make a file (no extension!!!) with the fully qualified name of our interface (team.hdt.blockadia.engine.core_rewrite.mod.IMod).
 * In that file, add the fully qualified name of your main mod file (for this file, ex., team.hdt.blockadia.engine.core_rewrite.mod.ModContainer).
 * <br/><br/>
 * Once you have that done, the mod should be good to go. If your mod does not have a mod name or the version of Blockadia you made the
 * mod for is not the same version as the Blockadia you run it on, the game will crash. This is on purpose to prevent conflicts that could
 * break the game.
 * <br/><br/>
 * Enjoy!
 * 
 * @author HuskyTheArtist
 *
 */
public class ModContainer implements IMod {

	@Override
	public String getModVersion() {
		return "0.0.1";
	}

	@Override
	public String getModName() {
		return "testmod";
	}

	@Override
	public String getGameVersionForMod() {
		return Blockadia.VERSION;
	}

	@Override
	public void preInit() {
		logger().info(this.getModName().toUpperCase() + " pre initialized okay!");
	}

	@Override
	public void init() {
		logger().info(this.getModName().toUpperCase() + " initialized okay!");
	}

	@Override
	public void postInit() {
		logger().info(this.getModName().toUpperCase() + " post initialized okay!");
	}
}