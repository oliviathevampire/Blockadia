package team.hdt.blockadia.engine.core_rewrite.game;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.EntityRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.three_d.AxisAlignedBB;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

import javax.annotation.Nonnull;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A basic, abstract, object that can be added to the game. It has an x and a y position.
 * 
 * @author Ocelot5836
 * @author Hypeirochus
 */
public abstract class GameObject {

	/** The x and y position of the object */
	private float x, y;
	/** The last x and y position of the object */
	private float lastX, lastY;

	/**
	 * Updates the object. Called 60 times per second.
	 */
	public abstract void update();

	/**
	 * Called when the object is rendered. Does not actually render the object.
	 * 
	 * @param renderer
	 *            The renderer for the game
	 * @param entityRenderer
	 *            The renderer that rendered this entity
	 */
	public abstract void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks);

	/**
	 * Used for collisions. If you want to use null, use {@link AxisAlignedBB#EMPTY_AABB} instead.
	 * 
	 * @return The x, y, and size of the entity
	 */
	@Nonnull
	public abstract AxisAlignedBB getCollisionBox();

	/**
	 * Used when rendering to texture this object.
	 * 
	 * @return The texture of this object
	 */
	@Nonnull
	public abstract Identifier getTexture();

	/**
	 * Used when rendering to texture this object. Only really used in a texture atlas.
	 * 
	 * @return The offset
	 */
	@Nonnull
	public abstract Vector2f getTextureOffset();

	/**
	 * This is used in a texture atlas when rendering. This cannot be zero
	 * 
	 * @return The width of the texture in tiles
	 */
	public int getTextureWidth() {
		return 1;
	}

	/**
	 * @return This object's x position
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return This object's y position
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return This object's last x position
	 */
	public float getLastX() {
		return lastX;
	}

	/**
	 * @return This object's last y position
	 */
	public float getLastY() {
		return lastY;
	}

	/**
	 * @return This object's rendering x position
	 */
	public float getRenderX(float partialTicks) {
		return lastX + (x - lastX) * partialTicks;
	}

	/**
	 * @return This object's rendering y position
	 */
	public float getRenderY(float partialTicks) {
		return lastY + (y - lastY) * partialTicks;
	}

	/**
	 * Sets this entity's position.
	 * 
	 * @param x
	 *            The new x position for the entity
	 * @param y
	 *            The new y position for the entity
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets this entity's x position
	 * Sets this entity's x position.
	 * 
	 * @param x
	 *            The new x position for the entity
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets this entity's y position
	 * Sets this entity's y position.
	 * 
	 * @param y
	 *            The new y position for the entity
	 */
	public void setY(float y) {
		this.y = y;
	}

	
	/**
	 * Sets this entity's last position.
	 * 
	 * @param lastX
	 *            The new last x position for the entity
	 * @param lastY
	 *            The new last y position for the entity
	 */
	public void setLastPosition(float lastX, float lastY) {
		this.lastX = lastX;
		this.lastY = lastY;
	}
	
	/**
	 * Sets this entity's last x position.
	 * 
	 * @param lastX
	 *            The new last x position for the entity
	 */
	public void setLastX(float lastX) {
		this.lastX = lastX;
	}
	
	/**
	 * Sets this entity's last y position.
	 * 
	 * @param lastY
	 *            The new last y position for the entity
	 */
	public void setLastY(float lastY) {
		this.lastY = lastY;
	}
}
