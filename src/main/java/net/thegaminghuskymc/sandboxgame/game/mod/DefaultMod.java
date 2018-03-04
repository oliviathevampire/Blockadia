package net.thegaminghuskymc.sandboxgame.game.mod;

import net.thegaminghuskymc.sandboxgame.engine.modding.IMod;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModInfo;

@ModInfo(name = "DefaultMod", modid = "test_mod", author = "TheGamingHuskyMC", version = "0.0.1", clientProxy = "net.thegaminghuskymc.sandboxgame.game.client.defaultmod.GameEngineDefaultModClient")
public class DefaultMod implements IMod {

    @Override
    public void initialize(Mod mod) {
        mod.addResource(new Blocks());
    }

    @Override
    public void deinitialize(Mod mod) {

    }

    /*@Override
    public void preInit(Mod mod) {
        mod.addResource(new Blocks());
        mod.addResource(new Events());
    }

    @Override
    public void init(Mod mod) {

    }

    @Override
    public void postInit(Mod mod) {

    }*/

}