package team.hdt.sandboxgame.game_engine.client.gui.userInterfaces;

import team.hdt.sandboxgame.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.sandboxgame.game_engine.client.gui.mainGuis.GuiRepository;
import team.hdt.sandboxgame.game_engine.client.guis.GuiComponent;
import team.hdt.sandboxgame.game_engine.client.guis.GuiTexture;
import team.hdt.sandboxgame.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.sandboxgame.game_engine.util.toolbox.Maths;

public class GuiScrollBar extends GuiComponent {

	private static final int PX_SCROLL_PER_TICK = 28;

	private final GuiTexture background;

	private float currentY = 0;
	private float scaleFactor = 1;

	private GuiBar bar;
	private float scrollWheel = 0;

	public GuiScrollBar(float scaleFactor) {
		this.background = new GuiTexture(GuiRepository.BLOCK);
		background.setOverrideColour(ColourPalette.LIGHT_GREY);
		setScaleValue(scaleFactor);
	}

	@Override
	protected void init() {
		super.init();
		bar = new GuiBar();
		super.addComponent(bar, 0, currentY, 1, 1f / scaleFactor);
	}

	protected void setScrollWheelAmount(float scrollWheel) {
		this.scrollWheel = scrollWheel;
	}

	protected void setScaleFactor(float scaleFactor) {
		setScaleValue(scaleFactor);
		this.currentY = 0;
		if (bar != null) {
			updateBarPosition();
		}
	}

	protected float getDesiredRelativeY() {
		return -currentY * scaleFactor;
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		background.setPosition(position.x, position.y, scale.x, scale.y);
	}

	@Override
	protected void updateSelf() {
		checkBarMoving();
	}
	
	private void setScaleValue(float factor){
		this.scaleFactor = Math.max(factor, 1f);
		show(factor > 1);
	}

	private void updateBarPosition() {
		bar.setRelativeY(currentY);
		bar.setRelativeScaleY(1f / scaleFactor);
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		data.addTexture(getLevel(), background);
	}

	private void checkBarMoving() {
		if (bar.isGrabbed()) {
			determineScroll(bar.getGrabPosition());
		} else if (super.isMouseOver()/* && MyMouse.getActiveMouse().isLeftClick()*/ && !bar.isMouseOver()) {
			determineScroll(0.5f);
		} else {
			doMouseWheelScroll();
		}
	}

	private void determineScroll(float grabPosition) {
		float mousePosition = getRelativeMouseY();
		float barPosition = mousePosition - (grabPosition * bar.getRelativeScaleY());
		this.currentY = Maths.clamp(barPosition, 0, 1 - (1f / scaleFactor));
		updateBarPosition();
	}

	private void doMouseWheelScroll() {
		currentY -= (scrollWheel * PX_SCROLL_PER_TICK) / (super.getPixelHeight() * scaleFactor);
		this.currentY = Maths.clamp(currentY, 0, 1 - (1f / scaleFactor));
		updateBarPosition();
	}

}
