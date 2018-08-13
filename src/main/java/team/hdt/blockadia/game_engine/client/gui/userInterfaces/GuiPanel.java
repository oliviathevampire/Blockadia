package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.gui.mainGuis.GuiRepository;
import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.util.Display;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ConstantDriver;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.SlideDriver;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ValueDriver;

public class GuiPanel extends GuiComponent {

	private GuiTexture inner;
	private GuiTexture outer;
	private boolean border = false;
	private boolean hideBorder = false;
	private int borderPixels;
	private boolean plain = false;

	public GuiPanel(){
		this.plain = true;
	}
	
	public GuiPanel(Colour colour) {
		this.outer = new GuiTexture(GuiRepository.BLOCK);
		outer.setOverrideColour(colour);
	}

	public GuiPanel(Texture innerTexture, Colour colour) {
		this.outer = new GuiTexture(innerTexture);
		outer.setOverrideColour(colour);
	}

	public GuiPanel(Colour colour, float alpha) {
		this.outer = new GuiTexture(GuiRepository.BLOCK);
		outer.setOverrideColour(colour);
		outer.setAlphaDriver(new ConstantDriver(alpha));
	}

	public GuiPanel(Colour colour, int borderPixels) {
		this.inner = new GuiTexture(GuiRepository.BLOCK);
		inner.setOverrideColour(colour);
		this.borderPixels = borderPixels;
		hideBorder = true;
		border = true;
	}

	public GuiPanel(Colour colour, int borderPixels, Colour borderColour) {
		this.inner = new GuiTexture(GuiRepository.BLOCK);
		inner.setOverrideColour(colour);
		this.outer = new GuiTexture(GuiRepository.BLOCK);
		outer.setOverrideColour(borderColour);
		this.borderPixels = borderPixels;
		border = true;
	}

	public GuiPanel(Texture innerTexture, Colour colour, int borderPixels, Colour borderColour) {
		this.inner = new GuiTexture(innerTexture);
		inner.setOverrideColour(colour);
		this.outer = new GuiTexture(GuiRepository.BLOCK);
		outer.setOverrideColour(borderColour);
		this.borderPixels = borderPixels;
		border = true;
	}
	
	public void setBlurry(){
		outer.setBlurry(true);
	}

	public void setAlphaDriver(ValueDriver driver) {
		if (inner != null) {
			inner.setAlphaDriver(driver);
		}
		if (outer != null) {
			outer.setAlphaDriver(driver);
		}
	}
	
	public void fadeOut(float time){
		if (inner != null) {
			inner.setAlphaDriver(new SlideDriver(inner.getAlpha(), 0, time));
		}
		if (outer != null) {
			outer.setAlphaDriver(new SlideDriver(outer.getAlpha(), 0, time));
		}
	}

	public void showBorder(boolean show) {
		this.hideBorder = !show;
	}

	public void setColour(Colour colour) {
		if (border) {
			inner.setOverrideColour(colour.duplicate());
		} else {
			outer.setOverrideColour(colour);
		}
	}

	public void setBorderColour(Colour colour) {
		if (!hideBorder) {
			outer.setOverrideColour(colour);
		}
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		if(plain){
			return;
		}
		if (!hideBorder) {
			outer.setPosition(position.x, position.y, scale.x, scale.y);
		}
		if (border) {
			float borderWidth = borderPixels / (Display.getWidth() * scale.x);
			float borderHeight = borderPixels / (Display.getHeight() * scale.y);
			inner.setPosition(position.x + borderWidth * scale.x, position.y + borderHeight * scale.y,
					(1 - borderWidth * 2f) * scale.x, (1 - borderHeight * 2f) * scale.y);
		}
	}

	@Override
	protected void updateSelf() {
		if(plain){
			return;
		}
		if (!hideBorder) {
			outer.update();
		}
		if (border) {
			inner.update();
		}
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		if(plain){
			return;
		}
		if (!hideBorder) {
			data.addTexture(getLevel(), outer);
		}
		if (border) {
			data.addTexture(getLevel(), inner);
		}
	}

}
