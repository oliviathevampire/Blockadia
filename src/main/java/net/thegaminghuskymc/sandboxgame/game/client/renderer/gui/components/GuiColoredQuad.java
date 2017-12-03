package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;

public class GuiColoredQuad extends Gui {

	/** background texture */
	private final Color color;

	public GuiColoredQuad() {
		super();
		this.color = new Color(0);
	}

	public final void setColor(Color color) {
		this.color.set(color.getARGB());
	}

	public final void setColor(float r, float g, float b, float a) {
		this.color.set(r, g, b, a);
	}

	@Override
	protected void onRender(GuiRenderer guiRenderer) {
		Matrix4f matrix = super.getGuiToGLChangeOfBasis();
		guiRenderer.renderColoredQuad(this.color.getR(), this.color.getG(), this.color.getB(), this.color.getA(),
				this.getTransparency(), matrix);
	}

	@Override
	protected void onUpdate() {
	}

	@Override
	protected void onInitialized(GuiRenderer renderer) {
	}

	@Override
	protected void onDeinitialized(GuiRenderer renderer) {
	}
}
