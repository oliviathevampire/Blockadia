package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine.client.gui.mainGuis.GuiRepository;
import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;

public class GuiBar extends GuiComponent {
	
	private final Colour BAR_COLOUR_1 = ColourPalette.BASE_BLUE;
	private final Colour BAR_COLOUR_2 = ColourPalette.BASE_BLUE.duplicate().scale(1.2f);
	private final Colour BAR_COLOUR_3 = BAR_COLOUR_2.duplicate().scale(1.2f);
	
	private final GuiTexture bar;
	
	private boolean grabbed = false;
	private float grabPosition;
	
	protected GuiBar(){
		this.bar = new GuiTexture(GuiRepository.BLOCK);
		bar.setOverrideColour(BAR_COLOUR_1);
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		bar.setPosition(position.x, position.y, scale.x, scale.y);
	}

	protected boolean isGrabbed(){
		return grabbed;
	}
	
	protected float getGrabPosition(){
		return grabPosition;
	}
	
	@Override
	protected void updateSelf() {
		determineGrabbed();
		determineColour();
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		data.addTexture(getLevel(), bar);
		
	}
	
	private void determineGrabbed(){
		/*MyMouse mouse = MyMouse.getActiveMouse();
		if(mouse.isLeftClick() && super.isMouseOver()){
			grabbed = true;
			grabPosition = super.getRelativeMouseY();
		}
		if(mouse.isLeftClickRelease()){
			grabbed = false;
		}*/
	}
	
	private void determineColour(){
		if(grabbed){
			bar.setOverrideColour(BAR_COLOUR_3);
		}else if(super.isMouseOver()/* && !MyMouse.getActiveMouse().isLeftButtonDown()*/){
			bar.setOverrideColour(BAR_COLOUR_2);
		}else{
			bar.setOverrideColour(ColourPalette.BASE_BLUE);
		}
	}

}
