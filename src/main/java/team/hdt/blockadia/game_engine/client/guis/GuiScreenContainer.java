package team.hdt.blockadia.game_engine.client.guis;

import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;

public class GuiScreenContainer extends GuiComponent{
	
	private boolean mouseInGui = false;
	
	protected GuiScreenContainer(){
		super.forceInitialization(0, 0, 1, 1);
	}
	
	protected boolean isMouseInGui(){
		return mouseInGui;
	}

	@Override
	protected void updateSelf() {
		checkMouseOver();
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
	}
	
	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
	}
	
	protected void checkMouseOver(){
		for(GuiComponent childComponent : super.getComponents()){
			if(childComponent.isShown() && childComponent.isMouseOverFocusIrrelevant()){
				mouseInGui = true;
				return;
			}
		}
		mouseInGui = false;
	}

}
