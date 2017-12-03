package net.thegaminghuskymc.sandboxgame.game.client.defaultmod;

import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.game.mod.DefaultMod;

public class GameEngineDefaultModClient extends DefaultMod {

	/*@Override
	public void preInit(Mod mod) {
        mod.addResource(new ClientEvents());
        mod.addResource(new ClientBlockRenderers());
		super.preInit(mod);
	}

	@Override
	public void init(Mod mod) {
		super.init(mod);
	}

	@Override
	public void postInit(Mod mod) {
		super.postInit(mod);
	}*/

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
