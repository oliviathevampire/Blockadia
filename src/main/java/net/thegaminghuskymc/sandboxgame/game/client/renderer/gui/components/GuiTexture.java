package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLTexture;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiTextureEventTextureChanged;

public class GuiTexture extends Gui {

	/** background texture */
	private GLTexture glTexture;
	private float ux;
	private float uy;
	private float vx;
	private float vy;

	public GuiTexture() {
		super();
	}

	public final void setTexture(GLTexture glTexture, float ux, float uy, float vx, float vy) {
		super.stackEvent(new GuiTextureEventTextureChanged<GuiTexture>(this, glTexture, ux, uy, vx, vy));
		this.glTexture = glTexture;
		this.ux = ux;
		this.uy = uy;
		this.vx = vx;
		this.vy = vy;
	}

	public final void setTexture(float ux, float uy, float vx, float vy) {
		this.setTexture(this.glTexture, ux, uy, vx, vy);
	}

	public final void setTexture(GLTexture glTexture) {
		this.setTexture(glTexture, this.ux, this.uy, this.vx, this.vy);
	}

	public final GLTexture getGLTexture() {
		return (this.glTexture);
	}

	@Override
	protected void onRender(GuiRenderer guiRenderer) {
		if (this.glTexture != null) {
			Matrix4f matrix = super.getGuiToGLChangeOfBasis();
			guiRenderer.renderTexturedQuad(this.glTexture, this.ux, this.uy, this.vx, this.vy, this.getTransparency(),
					matrix);
		}
	}

	public final float getUx() {
		return (this.ux);
	}

	public final float getUy() {
		return (this.uy);
	}

	public final float getVx() {
		return (this.vx);
	}

	public final float getVy() {
		return (this.vy);
	}
}
