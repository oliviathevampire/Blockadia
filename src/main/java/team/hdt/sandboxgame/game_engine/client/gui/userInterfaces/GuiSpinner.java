package team.hdt.sandboxgame.game_engine.client.gui.userInterfaces;

import team.hdt.sandboxgame.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.sandboxgame.game_engine.client.gui.mainGuis.GuiRepository;
import team.hdt.sandboxgame.game_engine.client.guis.GuiComponent;
import team.hdt.sandboxgame.game_engine.client.guis.GuiTexture;
import team.hdt.sandboxgame.game_engine.client.rendering.fontRendering.Text;
import team.hdt.sandboxgame.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.sandboxgame.game_engine.common.Main;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.sandboxgame.game_engine.util.toolbox.Colour;
import team.hdt.sandboxgame.game_engine.util.visualFxDrivers.SlideDriver;

import java.util.ArrayList;
import java.util.List;

public class GuiSpinner extends GuiComponent {
	
	private static final float TEXT_PAD = 0.05f;
	private static final float FADE_TIME = 0.5f;
	private static final float SLIDE_SPEED = 1.3f;
	
	private int value;
	private int change;
	private GuiTexture background;
	
	private float arrowSectionWidth;
	private float fontSize;
	private Text text;
	private List<Listener> changeListeners = new ArrayList<Listener>();
	private float releasedTime = 0;
	private Text releasedText;
	
	public GuiSpinner(int start, int change, float fontSize){
		this.value = start;
		this.change = change;
		this.fontSize = fontSize;
		initTextures();
	}
	
	public int getValue(){
		return value;
	}
	
	public void switchOver(){
		if(releasedText!=null){
			super.deleteText(releasedText);
		}
		releasedTime = 0;
		this.value = 0;
		releasedText = text;
		initText();
		releasedText.setAlphaDriver(new SlideDriver(1,0, FADE_TIME));
	}
	
	public void setBackgroundColour(Colour colour){
		background.setOverrideColour(colour);
	}
	
	public void addChangeListener(Listener listener){
		changeListeners.add(listener);
	}
	
	public void setValue(int value){
		this.value = value;
		text.setText(getTextString());
	}
	
	@Override
	protected void init() {
		calcArrowSectionWidth();
		addButtons();
		initText();
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		background.setPosition(position.x, position.y, scale.x, scale.y);
	}

	@Override
	protected void updateSelf() {
		/*MyMouse mouse = MyMouse.getActiveMouse();
		if(super.isMouseOver()){
			change((int) Math.signum(mouse.getDWheel()));
		}*/
		updateReleasedText();
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		//guiTextures.add(background);
	}
	
	private void updateReleasedText(){
		if(releasedText!=null){
			releasedTime += Main.getDeltaSeconds();
			releasedText.increaseRelativeX(-SLIDE_SPEED * Main.getDeltaSeconds());
			if(releasedTime > FADE_TIME){
				super.deleteText(releasedText);
				releasedText = null;
			}
		}
	}
	
	private void initTextures(){
		background = new GuiTexture(GuiRepository.BLOCK);
		background.setOverrideColour(ColourPalette.LIGHT_GREY);
	}
	
	private void calcArrowSectionWidth(){
		this.arrowSectionWidth = super.getRelativeWidthCoords(0.5f);
	}
	
	private void initText(){
		this.text = Text.newText(getTextString()).rightAlign().setFontSize(fontSize).create();
		text.setColour(ColourPalette.WHITE);
		super.addText(text, 0, 0, 1 - (arrowSectionWidth+TEXT_PAD));
	}
	
	private void addButtons(){
		GuiButton up = new GuiButton(GuiRepository.PLUS);
		up.setPreferredPixelSize(8);
		up.addListener(on -> change(1));
		GuiButton down = new GuiButton(GuiRepository.MINUS);
		down.setPreferredPixelSize(8);
		down.addListener(on -> change(-1));
		super.addPixelCompCenterX(up, 1-arrowSectionWidth/2f, 0);
		super.addPixelCompCenterX(down, 1-arrowSectionWidth/2f, 0.5f);
		//super.addCenteredComponentX(up, 1-arrowSectionWidth/2f, 0, 0.5f);
		//super.addCenteredComponentX(down, 1-arrowSectionWidth/2f, 0.5f, 0.5f);
	}
	
	private String getTextString(){
		if(value == 0){
			return "";
		}
		String textString = value > 0 ? "+" : "";
		textString += value + "%";
		return textString;
	}
	
	private void notifyListeners(){
		for(Listener listener : changeListeners){
			listener.eventOccurred(true);
		}
	}
	
	private void change(int amount){
		if(amount == 0){
			return;
		}
		value += change * amount;
		text.setText(getTextString());
		notifyListeners();
	}

}
