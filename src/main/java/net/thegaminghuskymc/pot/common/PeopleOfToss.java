package net.thegaminghuskymc.pot.common;

import net.thegaminghuskymc.pot.common.blocks.POTBlocks;
import net.thegaminghuskymc.pot.common.entities.POTEntities;
import net.thegaminghuskymc.pot.common.items.POTItems;
import net.thegaminghuskymc.pot.common.world.POTWorlds;
import net.thegaminghuskymc.sandboxgame.engine.modding.IMod;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModInfo;

@ModInfo(name = "People of Toss", creator = "toss-dev", version = "1.0.0.a", modid = "pot", clientProxy = "net.thegaminghuskymc.pot.client.PeopleOfTossClient")
public class PeopleOfToss implements IMod {

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

}