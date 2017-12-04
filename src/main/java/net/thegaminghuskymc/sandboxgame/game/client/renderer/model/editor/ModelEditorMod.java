package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor;

import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IMod;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModInfo;

@ModInfo(name = "Model Editor Module", author = "rpereira-dev", version = "0.0.0.a", modid = "mem")
public class ModelEditorMod implements IMod, IModResource {

	/*@Override
	public void preInit(Mod mod) {

	}

	@Override
	public void init(Mod mod) {

	}

	@Override
	public void postInit(Mod mod) {

	}*/

    public static int WORLD_ID = 0;

    @Override
    public void initialize(Mod mod) {

    }

    @Override
    public void deinitialize(Mod mod) {

    }

    @Override
    public void load(Mod mod, ResourceManager manager) {
        WORLD_ID = manager.getWorldManager().registerWorld(new ModelEditorWorld());
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {

    }
}
