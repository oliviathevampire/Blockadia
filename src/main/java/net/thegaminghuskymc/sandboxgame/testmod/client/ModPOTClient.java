package net.thegaminghuskymc.sandboxgame.testmod.client;

import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.testmod.client.render.models.POTModels;
import net.thegaminghuskymc.sandboxgame.testmod.common.ModPOT;

public class ModPOTClient extends ModPOT {

    @Override
    public void initialize(Mod mod) {
        super.initialize(mod);
        mod.addResource(new POTModels());
    }

    @Override
    public void deinitialize(Mod mod) {
        super.deinitialize(mod);
    }

}
