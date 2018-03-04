package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.world.WorldFlat;

public class ModelEditorWorld extends WorldFlat {

	public ModelEditorWorld() {
		super();
	}

	@Override
	public void onLoaded() {
		this.setWorldGenerator(new ModelEditorWorldGenerator());
		for (int x = -4; x < 4; x++) {
			for (int y = -4; y < 4; y++) {
				Terrain terrain = this.spawnTerrain(new Terrain(x, y, -1));
				this.generateTerrain(terrain);
			}
		}
	}

	@Override
	public String getName() {
		return ("ModelEditorWorld");
	}
}
