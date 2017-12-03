package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event;

import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLTexture;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiTexture;

public class GuiTextureEventTextureChanged<T extends GuiTexture> extends GuiTextureEvent<T> {

	/** new values */
	private GLTexture newTexture;
	private float newUx;
	private float newUy;
	private float newVx;
	private float newVy;

	public GuiTextureEventTextureChanged(T gui, GLTexture newTexture, float newUx, float newUy, float newVx,
                                         float newVy) {
		super(gui);
		this.newTexture = newTexture;
		this.newUx = newUx;
		this.newUy = newUy;
		this.newVx = newVx;
		this.newVy = newVy;
	}

	/** get the texture before this event was raised */
	public final GLTexture getNewGLTexture() {
		return (this.newTexture);
	}

	/** get the ux before this event was raised */
	public final float getNewUx() {
		return (this.newUx);
	}

	/** get the uy before this event was raised */
	public final float getNewUy() {
		return (this.newUy);
	}

	/** get the vx before this event was raised */
	public final float getNewVx() {
		return (this.newVx);
	}

	/** get the vy before this event was raised */
	public final float getNewVy() {
		return (this.newVy);
	}
}
