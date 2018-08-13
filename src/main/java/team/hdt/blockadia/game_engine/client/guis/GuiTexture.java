package team.hdt.blockadia.game_engine.client.guis;

import team.hdt.blockadia.game_engine.client.ClientMain;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ConstantDriver;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ValueDriver;

/**
 * A textured quad, making up part of a GUI component.
 * @author Karl
 *
 */
public class GuiTexture {
	
	private Texture texture;
	
	private Vectors2f position = new Vectors2f();
	private Vectors2f scale = new Vectors2f();
	
	private boolean usesBlur = false;
	
	private int[] scissorTestInfo = null;
	
	private ValueDriver alphaDriver = new ConstantDriver(1);
	
	private float alpha = 1;
	private boolean additive = false;
	private boolean flipTexture = false;
	
	private Colour overrideColour;
	
	public GuiTexture(Texture texture){
		this.texture = texture;
	}
	
	public GuiTexture(Texture texture, boolean flip){
		this.texture = texture;
		this.flipTexture = flip;
	}
	
	public boolean usesBlur(){
		return usesBlur;
	}
	
	public void setAdditive(){
		this.additive = true;
	}
	
	public void update(){
		alpha = alphaDriver.update(ClientMain.getDeltaSeconds());
	}
	
	public boolean hasOverrideColour(){
		return overrideColour!=null;
	}
	
	public boolean isAdditive(){
		return additive;
	}
	
	public void setBlurry(boolean blur){
		this.usesBlur = blur;
	}
	
	public void flip(boolean flip){
		this.flipTexture = flip;
	}
	
	public void setClippingBounds(int[] clippingBounds){
		this.scissorTestInfo = clippingBounds;
	}
	
	public Colour getOverrideColour(){
		return overrideColour;
	}
	
	public void setOverrideColour(Colour colour){
		this.overrideColour = colour;
	}
	
	public int[] getClippingBounds(){
		return scissorTestInfo;
	}
	
	public void setAlphaDriver(ValueDriver driver){
		this.alphaDriver = driver;
	}
	
	public void setPosition(float x, float y, float width, float height){
		position.set(x, y);
		scale.set(width, height);
	}
	
	public void setWidth(float scaleX){
		this.scale.x = scaleX;
	}

	public Texture getTexture() {
		return texture;
	}
	
	public boolean isFlipTexture(){
		return flipTexture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vectors2f getPosition() {
		return position;
	}
	
	public float getAlpha(){
		return alpha;
	}
	
	public ValueDriver getAlphaDriver(){
		return alphaDriver;
	}

	public Vectors2f getScale() {
		return scale;
	}

}
