package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine.client.gui.mainGuis.GuiRepository;
import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.fontRendering.Text;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.test.Main;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.languages.GameText;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;
import team.hdt.blockadia.game_engine.util.toolbox.Maths;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ConstantDriver;

public class ProgressBarUi extends GuiComponent {

	private static final String PROGRESS = GameText.getText(863);

	private static final int ARROW_COUNT = 3;
	private static final int ARROW_SIZE_PX = 6;

	private static final float ARROW_GAP = 0.04f;
	private static final float ARROW_START_X = 0.2f;

	private static final float ARROW_TIME_DIF = 0.1f;
	private static final float ARROW_FADE_TIME = 0.5f;
	private static final float ARROW_HALF_FADE_TIME = ARROW_FADE_TIME / 2f;

	private final GuiTexture barTexture;
	private final GuiTexture backgroundTexture;

	private Text text;
	private float progress;
	private boolean showPercent = true;
	private int max;

	private Colour barColour;
	private boolean flash = false;

	private float alphaProgress = 0;

	private GuiImage[][] arrows;

	public ProgressBarUi(float progress) {
		setProgress(progress);
		this.barTexture = new GuiTexture(GuiRepository.BLOCK);
		this.barColour = ColourPalette.GREEN.duplicate();
		this.barTexture.setOverrideColour(barColour);
		this.backgroundTexture = new GuiTexture(GuiRepository.BLOCK);
		this.backgroundTexture.setOverrideColour(ColourPalette.LIGHT_GREY);
	}

	public ProgressBarUi(float progress, GuiTexture barTexture) {
		setProgress(progress);
		this.barTexture = barTexture;
		this.barColour = ColourPalette.GREEN.duplicate();
		this.barTexture.setOverrideColour(barColour);
		this.backgroundTexture = new GuiTexture(GuiRepository.BLOCK);
		this.backgroundTexture.setOverrideColour(ColourPalette.LIGHT_GREY);
	}

	public void showCountingText(int max, Colour colour, float fontSize, float y) {
		this.max = max;
		this.showPercent = false;
		this.showText(colour, fontSize, y);
	}

	public void showText(Colour colour, float fontSize, float y) {
		text = Text.newText(getTextString()).center().setFontSize(fontSize).create();
		text.setColour(colour);
		super.addText(text, 0, y, 1);
	}

	public void setBarColour(Colour colour) {
		this.barColour = colour.duplicate();
		this.barTexture.setOverrideColour(barColour);
	}

	public void setBackgroundColour(Colour colour) {
		backgroundTexture.setOverrideColour(colour);
	}

	public void setBackgroundAlpha(float alpha) {
		backgroundTexture.setAlphaDriver(new ConstantDriver(alpha));
	}

	public void setProgress(float progress) {
		this.progress = Math.min(1, progress);
		if (text != null) {
			text.setText(getTextString());
		}
	}
	
	public void flashArrows(boolean flash){
		this.flash = flash;
		if(super.isInitialized()){
			showArrows();
		}
	}
	
	private void showArrows(){
		for(int i=0;i<arrows.length;i++){
			for(int j=0;j<arrows[i].length;j++){
				arrows[i][j].show(flash);
			}
		}
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		backgroundTexture.setPosition(position.x, position.y, scale.x, scale.y);
		barTexture.setPosition(position.x, position.y, scale.x * progress, scale.y);
	}

	@Override
	protected void updateSelf() {
		alphaProgress += Main.getDeltaSeconds() * 0.7f;
		alphaProgress %= 1;
		barTexture.update();
		backgroundTexture.update();
		barTexture.setWidth(calculateBarWidth());
		if (flash) {
			updateArrows();
		}
	}

	@Override
	protected void init() {
		super.init();
		this.arrows = new GuiImage[2][ARROW_COUNT];
		for (int i = 0; i < arrows.length; i++) {
			GuiImage[] arrowRow = arrows[i];
			for (int j = 0; j < arrowRow.length; j++) {
				arrows[i][j] = new GuiImage(GuiRepository.RIGHT_ARROW);
				super.addCenteredComponentY(arrows[i][j], 0.5f, 0.5f * i + ARROW_START_X + j * ARROW_GAP,
						super.pixelsToRelativeX(ARROW_SIZE_PX));
			}
		}
		showArrows();
	}

	private void updateArrows() {
		for (int i = 0; i < arrows.length; i++) {
			GuiImage[] arrowRow = arrows[i];
			for (int j = 0; j < arrowRow.length; j++) {
				float offset = ARROW_TIME_DIF * j;
				ConstantDriver alpha = ((ConstantDriver) arrows[i][j].getTexture().getAlphaDriver());
				alpha.setValue(calcAlpha(0 + offset, ARROW_HALF_FADE_TIME + offset, ARROW_FADE_TIME + offset));
			}
		}
	}

	private float calcAlpha(float start, float peak, float end) {
		if (alphaProgress <= start) {
			return 0;
		} else if (alphaProgress >= end) {
			return 0;
		} else if (alphaProgress < peak) {
			float blend = (alphaProgress - start) / (peak - start);
			return Maths.smoothInterpolate(0, 1, blend);
		} else {
			float blend = (alphaProgress - peak) / (end - peak);
			return Maths.smoothInterpolate(1, 0, blend);
		}
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		data.addTexture(getLevel(), backgroundTexture);
		data.addTexture(getLevel(), barTexture);
	}

	private int getPercent() {
		return (int) (progress * 100);
	}

	private String getTextString() {
		if (showPercent) {
			return PROGRESS + ": " + getPercent() + "%";
		} else {
			int current = (int) (max * progress);
			return current + "/" + max;
		}
	}

	private float calculateBarWidth() {
		return getScale().x * progress;
	}

}
