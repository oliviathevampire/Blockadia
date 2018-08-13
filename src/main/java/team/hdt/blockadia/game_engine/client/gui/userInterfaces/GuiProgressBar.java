package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine.client.gui.mainGuis.GuiRepository;
import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;

public class GuiProgressBar extends GuiComponent {

	private static final int BORDER_SIZE = 1;

	private static final Colour GOOD_COLOUR = ColourPalette.GREEN;
	private static final Colour BAD_COLOUR = ColourPalette.FLAT_RED;

	private float progress;

	private GuiTexture outlineTexture;
	private GuiTexture barTexture;
	private GuiTexture emptyTexture;

	public GuiProgressBar(float progress) {
		this.emptyTexture = new GuiTexture(GuiRepository.BLOCK);
		emptyTexture.setOverrideColour(ColourPalette.MIDDLE_GREY);
		this.outlineTexture = new GuiTexture(GuiRepository.BLOCK);
		outlineTexture.setOverrideColour(ColourPalette.BEIGE);
		this.barTexture = new GuiTexture(GuiRepository.BLOCK);
		barTexture.setOverrideColour(Colour.interpolateColours(BAD_COLOUR, GOOD_COLOUR, progress, null));
		this.progress = progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		outlineTexture.setPosition(position.x, position.y, scale.x, scale.y);
		float padX = super.pixelsToRelativeX(BORDER_SIZE);
		float padY = super.pixelsToRelativeY(BORDER_SIZE);
		float height = 1 - 2 * padY;
		barTexture.setPosition(getBarPos(position.x, scale.x, padX), getBarPos(position.y, scale.y, padY),
				calculateBarWidth(), scale.y * height);
		emptyTexture.setPosition(getBarPos(position.x, scale.x, padX), getBarPos(position.y, scale.y, padY),
				scale.x * (1 - 2 * padX), scale.y * height);
	}

	@Override
	protected void updateSelf() {
		barTexture.setOverrideColour(Colour.interpolateColours(BAD_COLOUR, GOOD_COLOUR, progress, null));
		barTexture.setWidth(calculateBarWidth());
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		data.addTexture(getLevel(), outlineTexture);
		data.addTexture(getLevel(), emptyTexture);
		data.addTexture(getLevel(), barTexture);
	}

	private float getBarPos(float pos, float scale, float pad) {
		return pos + scale * pad;
	}

	private float calculateBarWidth() {
		float padX = super.pixelsToRelativeX(BORDER_SIZE);
		float width = (1 - 2 * padX) * progress;
		return super.getScale().x * width;
	}

}
