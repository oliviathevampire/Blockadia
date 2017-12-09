package net.thegaminghuskymc.sandboxgame.testmod.common.entities;

import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;

public class POTEntities implements IModResource {

    // entities id
    public static int PLANT;
    public static int BIPED_TEST;

    @Override
    public void load(Mod mod, ResourceManager manager) {
        PLANT = manager.getEntityManager().registerEntity(EntityPlant.class);
        BIPED_TEST = manager.getEntityManager().registerEntity(EntityBipedTest.class);
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }

}