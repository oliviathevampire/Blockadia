package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.Main;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.SinWaveDriver;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ValueDriver;

public class ScalingImageUi extends GuiImage{
	
	private static final float SIZE_UP_X = 0.02f;
	private static final float SIZE_UP_Y = 0.04f;
	
	private ValueDriver sinDriver = new SinWaveDriver(0, 1, 3f);
	
	private float xScaleValue = 1;
	private float yScaleValue = 1;

	public ScalingImageUi(GuiTexture image) {
		super(image);
	}
	
	public ScalingImageUi(Texture texture){
		super(texture);
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		float x = position.x - ((xScaleValue - 1) * 0.5f) * scale.x;
		float y = position.y - ((yScaleValue - 1) * 0.5f) * scale.y;
		super.getTexture().setPosition(x, y, scale.x * xScaleValue, scale.y * yScaleValue);
	}

	@Override
	protected void updateSelf() {
		super.updateSelf();
		float value = sinDriver.update(Main.getDeltaSeconds());
		this.xScaleValue = 1 + value * SIZE_UP_X;
		this.yScaleValue = 1 + (1-value) * SIZE_UP_Y;
		Vectors2f pos = super.getPosition();
		Vectors2f scale = super.getScale();
		updateGuiTexturePositions(pos, scale);
	}
	
	

}
