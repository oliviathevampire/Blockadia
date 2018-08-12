package team.hdt.sandboxgame.game_engine.client.gui.buttons;

import team.hdt.sandboxgame.game_engine.client.gui.GuiTexture;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors2f;

import java.util.List;

public abstract class Button implements IButton {
    @SuppressWarnings("unused")
    //TODO: add loader to buttons
    //private Loader loader;
    private String texture;
    private Vectors2f position, scale;
    private GuiTexture guiTexture;
    //TODO: add mouse io to buttons
    //private GameMouse mouse;
    private boolean isHidden = false;
    private boolean isHovering = false;

    public Button(/*Loader loader,*/ String texture, Vectors2f position, Vectors2f scale) {
        //this.loader = loader;
        this.texture = texture;
        this.position = position;
        this.scale = scale;
        this.guiTexture = new GuiTexture(/*loader.loadTexture(texture)*/0, position, scale);
    }

    public void checkHover() {
        if (!isHidden) {
            Vectors2f location = guiTexture.getPosition();
            Vectors2f scale = guiTexture.getScale();
            Vectors2f mouseCoordinates = null;//mouse.getMousePoint(null);
            if (location.y + scale.y > -mouseCoordinates.y && location.y - scale.y < -mouseCoordinates.y
                    && location.x + scale.x > mouseCoordinates.x && location.x - scale.x < mouseCoordinates.x) {
                whileHover();
                if (!isHovering) {
                    isHovering = true;
                    startHover();
                }
				/*while (Mouse.next())
					if (KeyBoardIO.keys[GLFW.GLFW_MOUSE_BUTTON_RIGHT])
						onClick();
			} else {
				if (isHovering) {
					isHovering = false;
					stopHover();
				}*/
                guiTexture.setScale(this.scale);
            }
        }
    }

    public void playHoverAnimation(float scaleFactor) {
        guiTexture.setScale(new Vectors2f(scale.x + scaleFactor, scale.y + scaleFactor));
    }

    public void playerClickAnimation(float scaleFactor) {
        guiTexture.setScale(new Vectors2f(scale.x - (scaleFactor * 2), scale.y - (scaleFactor * 2)));
    }

    private void Render(List<GuiTexture> guiTextureList) {
        guiTextureList.add(guiTexture);
    }

    public void show_hide(List<GuiTexture> guiTextures) {
        Render(guiTextures);
    }

    public boolean isHovering() {
        return isHovering;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public GuiTexture getGuiTexture() {
        return guiTexture;
    }

    public Vectors2f getScale() {
        return scale;
    }

    public void setScale(Vectors2f scale) {
        guiTexture.setScale(scale);
    }

    public Vectors2f getPosition() {
        return position;
    }

    public void setPosition(Vectors2f position) {
        guiTexture.setPosition(position);
    }

    public String getTexture() {
        return texture;
    }
}
