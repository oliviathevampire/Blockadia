package net.thegaminghuskymc.sandboxgame.game.mod;

import net.thegaminghuskymc.sandboxgame.engine.modding.IMod;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModInfo;

@ModInfo(name = "Blockitect", modid = "blockitect", creator = "TheGamingHuskyMC", version = "0.0.1", clientProxy = "net.thegaminghuskymc.blockitect.game.client.default_mod.BlockitectClient")
public class Blockitect implements IMod {

    @Override
    public void initialize(Mod mod) {
        mod.addResource(new Blocks());
    }

    @Override
    public void deinitialize(Mod mod) {

    }

}