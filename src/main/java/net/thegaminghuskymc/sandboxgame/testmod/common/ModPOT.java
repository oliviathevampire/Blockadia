package net.thegaminghuskymc.sandboxgame.testmod.common;

import net.thegaminghuskymc.sandboxgame.engine.modding.IMod;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModInfo;
import net.thegaminghuskymc.sandboxgame.testmod.common.blocks.POTBlocks;
import net.thegaminghuskymc.sandboxgame.testmod.common.entities.POTEntities;
import net.thegaminghuskymc.sandboxgame.testmod.common.items.POTItems;
import net.thegaminghuskymc.sandboxgame.testmod.common.world.POTWorlds;

@ModInfo(name = "Default Common 'People of Toss' mod", author = "toss-dev", version = "1.0.0.a", modid = "pot", clientProxy = "net.thegaminghuskymc.sandboxgame.testmod.client.ModPOTClient")
public class ModPOT implements IMod {

	@Override
	public void initialize(Mod mod) {
		mod.addResource(new POTBlocks());
		mod.addResource(new POTItems());
		mod.addResource(new POTEntities());
		mod.addResource(new POTWorlds());
	}

	@Override
	public void deinitialize(Mod mod) {

	}

	/*@Override
	public void preInit(Mod mod) {
		mod.addResource(new POTBlocks());
		mod.addResource(new POTItems());
		mod.addResource(new POTEntities());
		mod.addResource(new POTWorlds());
	}

	@Override
	public void init(Mod mod) {

	}

	@Override
	public void postInit(Mod mod) {

	}*/

}