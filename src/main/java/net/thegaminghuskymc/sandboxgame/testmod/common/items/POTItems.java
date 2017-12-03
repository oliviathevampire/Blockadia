package net.thegaminghuskymc.sandboxgame.testmod.common.items;

import net.thegaminghuskymc.sandboxgame.engine.item.Item;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.Model;

public class POTItems implements IModResource {
	// items
	public static Item I_STICK;

	// items model
	public static Model M_STICK;

	// items textures id
	public static int T_STICK;

	@Override
	public void load(Mod mod, ResourceManager manager) {

	}

	@Override
	public void unload(Mod mod, ResourceManager manager) {
	}

}