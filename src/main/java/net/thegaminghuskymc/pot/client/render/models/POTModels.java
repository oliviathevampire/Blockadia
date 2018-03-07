package net.thegaminghuskymc.pot.client.render.models;


import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.Loader;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.OBJLoader;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.RawModel;

public class POTModels implements IModResource {

    private static OBJLoader objLoader = new OBJLoader();

    private static int registerJSONModel(ResourceManager manager, String modelPath, String texturePath,
                                         Class<? extends WorldEntity> entityClass) {
        return (registerModel(manager, OBJLoader.loadObjModel(modelPath, new Loader()), entityClass));
    }

    private static int registerModel(ResourceManager manager, RawModel modelInitializer,
                                     Class<? extends WorldEntity> entityClass) {
        return 0;
    }

    @Override
    public void load(Mod mod, ResourceManager manager) {
//        registerJSONModel(manager, R.getResPath("pot", "models/physicTest2/"), EntityBipedTest.class);
//        registerJSONModel(manager, R.getResPath("pot", "models/plant/"), EntityPlant.class);
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }

}
