package net.thegaminghuskymc.futopia.common;

import net.thegaminghuskymc.futopia.common.blocks.TechBlocks;
import net.thegaminghuskymc.sandboxgame.engine.modding.IMod;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModInfo;

@ModInfo(name = "Futopia", creator = "TheGamingHuskyMC", version = "0.0.1", modid = "tech_mod", clientProxy = "net.thegaminghuskymc.futopia.client.FutopiaClient")
public class Futopia implements IMod {

    @Override
    public void initialize(Mod mod) {
        mod.addResource(new TechBlocks());
    }

    @Override
    public void deinitialize(Mod mod) {

    }

}
