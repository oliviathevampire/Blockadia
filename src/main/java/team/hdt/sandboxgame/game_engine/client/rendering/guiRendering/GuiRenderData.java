package team.hdt.sandboxgame.game_engine.client.rendering.guiRendering;

import team.hdt.sandboxgame.game_engine.client.rendering.fontRendering.FontType;
import team.hdt.sandboxgame.game_engine.client.rendering.fontRendering.Text;
import team.hdt.sandboxgame.game_engine.client.guis.GuiTexture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiRenderData {
	
	private GuiRenderLevelData[] levels;
	
	public GuiRenderData(){
		levels = new GuiRenderLevelData[1];
		levels[0] = new GuiRenderLevelData();
	}
	
	public void addTexture(int level, GuiTexture texture){
		if(level >= levels.length){
			resize(level+1);
		}
		levels[level].textures.add(texture);
	}
	
	public void addText(int level, Text text){
		if(level >= levels.length){
			resize(level+1);
		}
		levels[level].addText(text);
	}
	
	public GuiRenderLevelData[] getRenderData(){
		return levels;
	}
	
	public void clear(){
		for(GuiRenderLevelData level : levels){
			level.clear();
		}
	}
	
	private void resize(int size){
		GuiRenderLevelData[] oldData = levels;
		levels = new GuiRenderLevelData[size];
		for(int i=0;i<oldData.length;i++){
			levels[i] = oldData[i];
		}
		for(int i=oldData.length;i<levels.length;i++){
			levels[i] = new GuiRenderLevelData();
		}
	}
	
	public static class GuiRenderLevelData{
		private List<GuiTexture> textures = new ArrayList<>();
		private Map<FontType, List<Text>> texts = new HashMap<>();
		
		public boolean isEmpty(){
			return textures.isEmpty() && texts.isEmpty();
		}
		
		public List<GuiTexture> getTextures(){
			return textures;
		}
		
		public Map<FontType, List<Text>> getTexts(){
			return texts;
		}
		
		private void clear(){
			textures.clear();
			texts.clear();
		}
		
		private void addText(Text text){
			FontType font = text.getFontType();
			List<Text> textBatch = texts.get(font);
			if (textBatch == null) {
				textBatch = new ArrayList<>();
				texts.put(font, textBatch);
			}
			textBatch.add(text);
		}
	}

}
