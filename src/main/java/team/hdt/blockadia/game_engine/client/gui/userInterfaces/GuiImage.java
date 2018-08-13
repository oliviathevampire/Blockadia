package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;

public class GuiImage extends GuiComponent {
	
	private GuiTexture image;
	
	public GuiImage(GuiTexture image){
		this.image = image;
	}
	
	public GuiImage(Texture image){
		this.image = new GuiTexture(image);
	}
	
	public GuiTexture getTexture(){
		return image;
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
			image.setPosition(position.x, position.y, scale.x, scale.y);
	}

	@Override
	protected void setTextureClippingBounds(int[] bounds) {
		image.setClippingBounds(bounds);
	}

	@Override
	protected void updateSelf() {
		image.update();
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		data.addTexture(getLevel(), image);	
	}

}
