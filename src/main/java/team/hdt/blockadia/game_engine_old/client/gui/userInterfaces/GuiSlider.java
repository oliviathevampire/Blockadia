package team.hdt.blockadia.game_engine_old.client.gui.userInterfaces;

import team.hdt.blockadia.game_engine_old.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine_old.client.gui.mainGuis.GuiRepository;
import team.hdt.blockadia.game_engine_old.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine_old.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine_old.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine_old.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.core.util.math.Maths;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine_old.util.toolbox.Colour;

import java.util.ArrayList;
import java.util.List;

public class GuiSlider extends GuiComponent {

    private static final float POINTER_WIDTH = 0.4f;
    private static final float SCROLL_WHEEL_SPEED = 0.035f;

    private final int pixelHeight;
    private final Colour pointerColour;
    private float progress;
    private boolean grabbed = false;
    private GuiImage pointer;
    private boolean equality = false;
    private ProgressUi progressUi;
    private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

    public GuiSlider(float startingValue) {
        this.progress = startingValue;
        this.pixelHeight = 3;
        this.pointerColour = ColourPalette.BRIGHT_GREY;
    }

    public GuiSlider(float startingValue, int pixelHeight, Colour pointerColour) {
        this.progress = startingValue;
        this.pixelHeight = pixelHeight;
        this.pointerColour = pointerColour;
    }

    public void addChangeListener(ChangeListener listener) {
        this.listeners.add(listener);
    }

    public void setEqualitySlider() {
        this.equality = true;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float prog) {
        this.progress = Maths.clamp(prog, 0, 1);
        pointer.setRelativeX(progress - super.getRelativeWidthCoords(1) * POINTER_WIDTH * 0.5f);
        progressUi.progress = progress;
        notifyListeners();
    }

    @Override
    protected void init() {
        super.init();
        pointer = new GuiImage(GuiRepository.BLOCK);
        pointer.getTexture().setOverrideColour(pointerColour);
        this.progressUi = new ProgressUi(progress);
        progressUi.equalitySlider = equality;
        float height = super.pixelsToRelativeY(pixelHeight);
        super.addComponent(progressUi, 0, (1 - height) / 2f, 1, height);
        pointer.setPreferredAspectRatio(POINTER_WIDTH);
        super.addCenteredComponentX(pointer, progress, 0, 1);
    }

    @Override
    protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {

    }

    @Override
    protected void updateSelf() {
		/*MyMouse mouse = MyMouse.getActiveMouse();
		boolean mouseOver = super.isMouseOver();
		if(mouseOver && mouse.isLeftClick()){
			this.grabbed = true;
		}else if(mouse.isLeftClickRelease()){
			this.grabbed = false;
		}
		if(grabbed){
			float mouseX = MyMouse.getActiveMouse().getX();
			setProgress((mouseX - super.getPosition().x)/super.getScale().x);
		}else if(mouseOver){
			float change = -mouse.getDWheelSigned();
			setProgress(progress + change * SCROLL_WHEEL_SPEED);
		}*/
    }


    @Override
    public boolean isMouseOverFocusIrrelevant() {
        if (!GuiMaster.isMouseInteractionEnabled()) {
            return false;
        }
        //TODO:what's MyMouse?
		/*MyMouse mouse = MyMouse.getActiveMouse();
		Vectors2f position = super.getPosition();
		Vectors2f scale = super.getScale();
		float extraX = (super.getRelativeWidthCoords(1.2f) * POINTER_WIDTH * 0.5f) * scale.x;
		if (mouse.getX() >= position.x-extraX && mouse.getX() <= position.x + scale.x + extraX) {
			if (mouse.getY() >= position.y && mouse.getY() <= position.y + scale.y) {
				return true;
			}
		}*/
        return false;
    }

    @Override
    protected void getGuiTextures(GuiRenderData data) {

    }

    private void notifyListeners() {
        for (ChangeListener listener : listeners) {
            listener.eventOccurred(progress);
        }
    }

    protected static class ProgressUi extends GuiComponent {

        private GuiTexture background;
        private GuiTexture foreground;

        private float progress;
        private boolean equalitySlider;

        public ProgressUi(float startingValue) {
            this.background = new GuiTexture(GuiRepository.BLOCK);
            background.setOverrideColour(ColourPalette.MIDDLE_GREY);
            this.foreground = new GuiTexture(GuiRepository.BLOCK);
            this.foreground.setOverrideColour(ColourPalette.GREEN);
            this.progress = startingValue;
        }

        @Override
        protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
            background.setPosition(position.x, position.y, scale.x, scale.y);
            foreground.setPosition(position.x, position.y, scale.x * (equalitySlider ? 1 : progress), scale.y);
        }

        @Override
        protected void updateSelf() {
            foreground.setWidth(super.getScale().x * (equalitySlider ? 1 : progress));
        }

        @Override
        protected void getGuiTextures(GuiRenderData data) {
            data.addTexture(getLevel(), background);
            data.addTexture(getLevel(), foreground);
        }

    }

}
