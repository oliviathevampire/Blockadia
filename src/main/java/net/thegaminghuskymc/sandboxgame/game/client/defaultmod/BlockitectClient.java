package net.thegaminghuskymc.sandboxgame.game.client.defaultmod;

import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.game.mod.Blockitect;

public class BlockitectClient extends Blockitect {

    @Override
    public void initialize(Mod mod) {
        super.initialize(mod);
        mod.addResource(new ClientEvents());
        mod.addResource(new ClientBlockRenderers());
    }

    @Override
    public void deinitialize(Mod mod) {
        super.deinitialize(mod);
    }
}
