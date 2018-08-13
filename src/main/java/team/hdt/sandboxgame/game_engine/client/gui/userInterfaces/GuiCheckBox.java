package team.hdt.sandboxgame.game_engine.client.gui.userInterfaces;

import team.hdt.sandboxgame.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.sandboxgame.game_engine.client.gui.mainGuis.GuiRepository;
import team.hdt.sandboxgame.game_engine.client.guis.GuiComponent;
import team.hdt.sandboxgame.game_engine.client.guis.GuiTexture;
import team.hdt.sandboxgame.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.sandboxgame.game_engine.client.rendering.textures.Texture;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.sandboxgame.game_engine.util.toolbox.Colour;

import java.util.ArrayList;
import java.util.List;

public class GuiCheckBox extends GuiComponent {

	private GuiTexture checkInner, checkOuter;
	private Colour backColour;

	private List<Listener> listeners = new ArrayList<Listener>();

	private boolean mousedOver = false;
	private boolean on = false;

	public GuiCheckBox(boolean on) {
		this.on = on;
		this.backColour = ColourPalette.LIGHT_GREY;
		checkInner = new GuiTexture(GuiRepository.TICK_FILL);
		checkInner.setOverrideColour(on ? ColourPalette.GREEN : backColour);
		checkOuter = new GuiTexture(GuiRepository.TICK_EMPTY);
		checkOuter.setOverrideColour(backColour);
		super.setPreferredPixelSize(18);
	}
	
	public GuiCheckBox(boolean on, Texture fillTexture, Texture emptyTexture, Colour col) {
		this.on = on;
		this.backColour = col;
		checkInner = new GuiTexture(fillTexture);
		checkInner.setOverrideColour(on ? ColourPalette.GREEN : backColour);
		checkOuter = new GuiTexture(emptyTexture);
		checkOuter.setOverrideColour(backColour);
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		checkInner.setPosition(position.x, position.y, scale.x, scale.y);
		checkOuter.setPosition(position.x, position.y, scale.x, scale.y);
	}

	@Override
	protected void updateSelf() {
		this.mousedOver = super.isMouseOver();
		if (mousedOver) {
			checkClick();
		}
		setColours();
	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		if (on || mousedOver) {
			data.addTexture(getLevel(), checkInner);
		} else {
			data.addTexture(getLevel(), checkOuter);
		}
	}

	private void setColours() {
		if(on){
			checkInner.setOverrideColour(ColourPalette.GREEN);
		}else if (mousedOver) {
			checkInner.setOverrideColour(backColour);
		}
	}

	private void checkClick() {
		/*MyMouse mouse = MyMouse.getActiveMouse();
		if (mouse.isLeftClick()) {
			set(!on);
		}*/
	}
	
	public void set(boolean checked){
		this.on = checked;
		notifyListeners(on);
	}

	private void notifyListeners(boolean on) {
		for (Listener listener : listeners) {
			listener.eventOccurred(on);
		}
	}

}
