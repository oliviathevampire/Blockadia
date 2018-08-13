package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine.client.ClientMain;
import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.*;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiClickable extends GuiComponent {

	private static final float TOOL_TIP_TIME = 0.5f;

	// TODO sigh, should have extended a class like this for GuiButton and text
	// button.

	protected static final float MAX_SIZE = 1.15f;
	protected static final float CHANGE_TIME = 0.07f;

	private float scaleFactor = MAX_SIZE;
	private boolean mouseOver;
	private ValueDriver scaleDriver = new ConstantDriver(1);
	private float currentScale = 1;

	private Vectors2f originalPosition;
	private Vectors2f originalScale;

	private List<ClickListener> listenersToAdd = new ArrayList<ClickListener>();
	private List<ClickListener> listeners = new ArrayList<ClickListener>();

	private Vectors2f unclickRelPos;
	private Vectors2f unclickRelScale;

	private Vectors2f unclickPos = new Vectors2f();
	private Vectors2f unclickScale = new Vectors2f();

	private boolean maintainPosX = false;
	private boolean blocked = false;

	private boolean toggleButton = false;
	private boolean toggledOn = false;

	private boolean firingListeners = false;

	private boolean manualTurnOffDisabled = false;

	private Integer hotkey = null;

	private boolean muted = false;

	public GuiClickable() {
	}

	public void mute() {
		this.muted = true;
	}

	public GuiClickable(float scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public GuiClickable(boolean toggleButton) {
		this.toggleButton = toggleButton;
	}

	public GuiClickable(boolean toggleButton, float scaleFactor) {
		this.toggleButton = toggleButton;
		this.scaleFactor = scaleFactor;
	}

	public void setScaleFactor(float scale) {
		this.scaleFactor = scale;
	}

	public void setMaintainPositionX(boolean maintain) {
		this.maintainPosX = maintain;
	}

	public void addListener(ClickListener listener) {
		if (firingListeners) {
			listenersToAdd.add(listener);
		} else {
			listeners.add(listener);
		}
	}

	public void setUnclickableRegion(Vectors2f pos, Vectors2f scale) {
		this.unclickRelPos = pos;
		this.unclickRelScale = scale;
		calculateUnclickRegion(super.getPosition(), super.getScale());
	}

	@Override
	public void setRelativeX(float x) {
		this.originalPosition.x = x;
		this.updatePositions();
	}

	public float getOriginalRelativeX() {
		return originalPosition.x;
	}

	public void block(boolean block) {
		if (this.blocked == block) {
			return;
		}
		this.blocked = block;
		if (block) {
			if (mouseOver) {
				mouseOffOccurred();
			}
		}
	}

	public void wobble(float factor, float time) {
		scaleDriver = new SinWaveDriver(1, factor, time);
	}

	public void bounce(float time, float factor) {
		// if (!mouseOver) {
		scaleDriver = new BounceDriver(currentScale, factor * currentScale, time);
		// }
	}

	public void cancelScaleEffect() {
		scaleDriver = new SlideDriver(currentScale, 1, CHANGE_TIME);
	}

	public void toggle() {
		toggledOn = !toggledOn;
		changeButtonState(toggledOn);
		notifyListeners(GuiClickEvent.newToggleEvent(toggledOn));
	}

	public void disableManualTurnOff() {
		this.manualTurnOffDisabled = true;
	}

	public void release() {
		if (toggledOn) {
			toggledOn = false;
			scaleDriver = new SlideDriver(currentScale, 1f, CHANGE_TIME);
		}
	}
	
	public void setHotkey(int key){
		this.hotkey = key;
	}

	public boolean isToggledOn() {
		return toggledOn;
	}

	@Override
	protected void updateSelf() {
		if (!blocked) {
			if (!toggledOn) {
				checkMouseOverEvents();
			}
			checkClickEvent();
			checkHotkey();
		}
		currentScale = scaleDriver.update(ClientMain.getDeltaSeconds());
		updatePositions();
	}

	private void checkHotkey() {
		/*if (hotkey != null) {
			if (MyKeyboard.getKeyboard().keyDownEventOccurred(hotkey)) {
				if (!toggledOn) {
					this.toggle();
				}
			} else if (MyKeyboard.getKeyboard().keyUpEventOccurred(hotkey)) {
				if (toggledOn) {
					toggle();
				}
			}
		}*/
	}

	protected boolean isBlocked() {
		return blocked;
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		if (originalPosition == null) {
			originalPosition = new Vectors2f(getRelativeX(), getRelativeY());
			originalScale = new Vectors2f(getRelativeScaleX(), getRelativeScaleY());
		}
		calculateUnclickRegion(position, scale);
	}

	protected void setOn() {
		toggledOn = true;
		scaleDriver = new ConstantDriver(scaleFactor);
	}

	private void checkClickEvent() {
		/*if (isMouseOverClickableRegion()) {
			if (MyMouse.getActiveMouse().isLeftClick()) {
				removeToolTip();
				if (toggleButton && !(toggledOn && manualTurnOffDisabled)) {
					if (!muted) {
						SoundMaestro.playSystemSound(GuiSounds.getClickSound());
					}
					toggle();
				} else {
					notifyListeners(GuiClickEvent.newLeftClickEvent(true));
				}
			} else if (MyMouse.getActiveMouse().isLeftClickRelease()) {
				notifyListeners(GuiClickEvent.newLeftClickEvent(false));
			}
		}*/
	}

	private void checkMouseOverEvents() {
		if (isMouseOver() && !mouseOver) {
			mouseOverOccurred();
		} else if (!isMouseOver() && mouseOver) {
			mouseOffOccurred();
		}
	}

	protected void changeButtonState(boolean toggleOn) {
		if (toggleOn) {
			scaleDriver = new ConstantDriver(scaleFactor);
		} else {
			scaleDriver = new SlideDriver(currentScale, 1f, CHANGE_TIME);
		}
	}

	protected void mouseOverOccurred() {
		mouseOver = true;
		scaleDriver = new SlideDriver(currentScale, scaleFactor, CHANGE_TIME);
		notifyListeners(GuiClickEvent.newMouseOverEvent(true));
	}

	protected void mouseOffOccurred() {
		mouseOver = false;
		scaleDriver = new SlideDriver(currentScale, 1, CHANGE_TIME);
		notifyListeners(GuiClickEvent.newMouseOverEvent(false));
	}

	@Override
	protected void delete() {
		super.delete();
	}

	private void updatePositions() {
		float currentX = maintainPosX ? originalPosition.x
				: calculateScaledPosition(originalPosition.x, originalScale.x);
		float currentY = calculateScaledPosition(originalPosition.y, originalScale.y);
		float currentScaleX = originalScale.x * currentScale;
		float currentScaleY = originalScale.y * currentScale;
		super.setRelativePosition(currentX, currentY);
		super.setRelativeScale(currentScaleX, currentScaleY);
	}

	private float calculateScaledPosition(float originalPos, float originalScale) {
		float change = (originalScale * currentScale) - originalScale;
		return originalPos - (change / 2);
	}

	private void addNewListeners() {
		for (ClickListener toAdd : listenersToAdd) {
			listeners.add(toAdd);
		}
		listenersToAdd.clear();
	}

	private void notifyListeners(GuiClickEvent event) {
		firingListeners = true;
		for (ClickListener listener : listeners) {
			listener.eventOccurred(event);
		}
		addNewListeners();
		firingListeners = false;
	}

	private boolean isMouseOverClickableRegion() {
		boolean mouseOver = super.isMouseOver();
		if (unclickRelPos == null || mouseOver == false) {
			return mouseOver;
		}
		return !mouseOverUnclickableRegion();
	}

	private void calculateUnclickRegion(Vectors2f position, Vectors2f scale) {
		if (unclickRelPos != null) {
			this.unclickPos.x = position.x + (unclickRelPos.x * scale.x);
			this.unclickPos.y = position.y + (unclickRelPos.y * scale.y);
			this.unclickScale.x = scale.x * unclickRelScale.x;
			this.unclickScale.y = scale.y * unclickRelScale.y;
		}
	}

	private boolean mouseOverUnclickableRegion() {
		/*MyMouse mouse = MyMouse.getActiveMouse();
		if (mouse.getX() >= unclickPos.x && mouse.getX() <= unclickPos.x + unclickScale.x) {
			if (mouse.getY() >= unclickPos.y && mouse.getY() <= unclickPos.y + unclickScale.y) {
				return true;
			}
		}*/
		return false;
	}

}
