package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.util.JSONHelper;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.Model;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.ModelBlockData;

import java.io.IOException;

public class JSONEditableModelInitializer extends JSONModelInitializer {

	public JSONEditableModelInitializer(String dirpath) {
		super(dirpath);
	}

	@Override
	protected void parseJSON(Model model, JsonObject jsonInfo) throws JsonParseException, IOException {
		super.parseJSON(model, jsonInfo);
		// get the blocks
		this.parseJSONBlocks((EditableModel) model, jsonInfo);
	}

	private void parseJSONBlocks(EditableModel model, JsonObject jsonInfo) {
		String blockspath = super.dirpath + jsonInfo.get("blocks");
		JsonObject blocks;
		try {
			blocks = new JsonObject().getAsJsonObject(JSONHelper.readFile(blockspath));
		} catch (Exception e) {
			e.printStackTrace(Logger.get().getPrintStream());
			return;
		}

		JsonArray layers = blocks.getAsJsonArray("layers");
		for (int i = 0; i < layers.size(); i++) {
			JsonObject layer = layers.get(i).getAsJsonObject();
			String name = layer.get("name").getAsString();
			float sizeUnit = (float) layer.get("sizeUnit").getAsDouble();
			EditableModelLayer editableModelLayer = new EditableModelLayer(name);
			editableModelLayer.setBlockSizeUnit(sizeUnit);
			model.setLayer(editableModelLayer);
		}

		JsonArray layersData = blocks.getAsJsonArray("layersData");
		for (int i = 0; i < layersData.size(); i++) {
			JsonObject layerData = layersData.get(i).getAsJsonObject();
			String layerName = layerData.get("layer").getAsString();

			EditableModelLayer editableModelLayer = model.getLayer(layerName);
			if (editableModelLayer == null) {
				continue;
			}
			JsonArray layerBlocksData = layerData.get("blocks").getAsJsonArray();

			for (int j = 0; j < layerBlocksData.size();) {
				int x = layerBlocksData.get(j++).getAsInt();
				int y = layerBlocksData.get(j++).getAsInt();
				int z = layerBlocksData.get(j++).getAsInt();
				String b1 = layerBlocksData.get(j++).getAsString();
				String b2 = layerBlocksData.get(j++).getAsString();
				String b3 = layerBlocksData.get(j++).getAsString();
				float w1 = (float) layerBlocksData.get(j++).getAsDouble();
				float w2 = (float) layerBlocksData.get(j++).getAsDouble();
				float w3 = (float) layerBlocksData.get(j++).getAsDouble();

				ModelBlockData blockData = new ModelBlockData(x, y, z);
				blockData.setBone(0, b1, w1);
				blockData.setBone(1, b2, w2);
				blockData.setBone(2, b3, w3);
				editableModelLayer.setBlockData(blockData);
			}
		}
	}
}
