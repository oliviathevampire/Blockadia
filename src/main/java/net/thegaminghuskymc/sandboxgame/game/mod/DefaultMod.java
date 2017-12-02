package net.thegaminghuskymc.sandboxgame.game.mod;

import net.thegaminghuskymc.sandboxgame.engine.modding.IMod;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModInfo;

@ModInfo(name = "DefaultMod", modid = "test_mod", author = "TheGamingHuskyMC", version = "0.0.1")
public class DefaultMod implements IMod {

    @Override
    public void preInit(Mod mod) {
        mod.preInit();
        mod.addResource(new Blocks());
    }

    @Override
    public void init(Mod mod) {
        mod.init();
    }

    @Override
    public void postInit(Mod mod) {
        mod.postInit();
    }

}