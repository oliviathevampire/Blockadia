package net.thegaminghuskymc.sandboxgame.testmod.client.render.models;


import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.Model;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelInitializer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.json.JSONModelInitializer;
import net.thegaminghuskymc.sandboxgame.game.client.resources.BlockRendererManager;
import net.thegaminghuskymc.sandboxgame.game.client.resources.ModelManager;
import net.thegaminghuskymc.sandboxgame.game.client.resources.ResourceManagerClient;
import net.thegaminghuskymc.sandboxgame.testmod.common.entities.EntityBipedTest;
import net.thegaminghuskymc.sandboxgame.testmod.common.entities.EntityPlant;
import net.thegaminghuskymc.sandboxgame.testmod.common.entities.EntityTest;

public class POTModels implements IModResource {

	@Override
	public void load(Mod mod, ResourceManager manager) {
		registerJSONModel(manager, R.getResPath("pot", "models/physicTest2/"), EntityBipedTest.class);
		registerJSONModel(manager, R.getResPath("pot", "models/animTest/"), EntityTest.class);
		registerJSONModel(manager, R.getResPath("pot", "models/plant/"), EntityPlant.class);
	}

	@Override
	public void unload(Mod mod, ResourceManager manager) {
	}

	private static int registerJSONModel(ResourceManager manager, String dirpath,
			Class<? extends Entity> entityClass) {
		return (registerModel(manager, new JSONModelInitializer(dirpath), entityClass));
	}

	private static int registerModel(ResourceManager manager, ModelInitializer modelInitializer,
			Class<? extends Entity> entityClass) {
		Model model = new Model(modelInitializer);
		ModelManager modelManager = ((ResourceManagerClient) manager).getModelManager();
		int modelID = modelManager.registerModel(model);
		modelManager.attachEntityToModel(entityClass, modelID);
		return (modelID);
	}

}
