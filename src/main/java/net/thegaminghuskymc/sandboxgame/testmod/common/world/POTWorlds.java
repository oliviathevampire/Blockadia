package net.thegaminghuskymc.sandboxgame.testmod.common.world;

import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;

public class POTWorlds implements IModResource {
    /**
     * default world id
     */
    public static int DEFAULT;

    @Override
    public void load(Mod mod, ResourceManager manager) {
        DEFAULT = manager.getWorldManager().registerWorld(new WorldDefault());
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }

}