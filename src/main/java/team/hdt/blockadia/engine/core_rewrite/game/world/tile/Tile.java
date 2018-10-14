package team.hdt.blockadia.engine.core_rewrite.game.world.tile;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.game.world.map.TileMap;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.TileRenderer;
import team.hdt.blockadia.engine.core_rewrite.util.AxisAlignedBB;
import team.hdt.blockadia.engine.core_rewrite.util.I18n;

import javax.annotation.Nullable;

public abstract class Tile {

	public static final AxisAlignedBB FULL = new AxisAlignedBB(0, 0, 16, 16);

	private Identifier registryName;
	private String unlocalizedName;

	protected boolean shouldRender;

	public Tile(Identifier registryName, String unlocalizedName) {
		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
		this.shouldRender = true;
	}

	/**
	 * Called when the tile is rendered. Please Note, this does <b><i>NOT</i></b> render the tile! This is only called when the tile is rendered.
	 * 
	 * @param x
	 *            The x position of the tile instance
	 * 
	 * @param y
	 *            The y position of the tile instance
	 * 
	 * @param renderer
	 *            the main rendering handler
	 * 
	 * @param tileRenderer
	 *            the renderer that actually renders the tile
	 */
	public void render(float x, float y, MasterRenderer renderer, TileRenderer tileRenderer) {
	}

	/**
	 * Updates the tile.
	 */
	public abstract void update();

	/**
	 * Called when the tile is added into the world.
	 * 
	 * @param map
	 *            The world the tile is being placed in.
	 * 
	 * @param x
	 *            The x position of where the tile is placed at.
	 * 
	 * @param y
	 *            The y position of where the tile is placed at.
	 */
	public void onTilePlaced(TileMap map, float x, float y) {
	}

	/**
	 * Called when the tile is removed from the world.
	 * 
	 * @param map
	 *            The world the tile is being removed from.
	 * 
	 * @param x
	 *            The x position of where the tile is removed from.
	 * 
	 * @param y
	 *            The y position of where the tile is removed from.
	 */
	public void onTileDestroyed(TileMap map, float x, float y) {
	}

	/**
	 * If the texture layer is zero, null is not valid. If the texture layer is greater than zero, then you may pass in null.
	 * 
	 * @param textureLayer
	 *            The layer that is being requested
	 * @return The texture coordinates the tile uses
	 */
	@Nullable
	public abstract Vector2f getTextureCoords(int textureLayer);

	/**
	 * @return The resource location for the texture the tile uses
	 */
	public abstract Identifier getTexture();

	/**
	 * @return The width (in tiles) of the texture atlas. This will <b><i>ONLY</i></b> work properly if you use a different texture than another tile with the same texture
	 */
	public int getTextureWidth() {
		return 1;
	}

	/**
	 * @return The collision box of the tile
	 */
	public AxisAlignedBB getCollisionBox() {
		return FULL;
	}

	/**
	 * @return The registry name of the tile
	 */
	public Identifier getRegistryName() {
		return registryName;
	}

	/**
	 * @return The name of the tile before it is localized
	 */
	public String getUnlocalizedName() {
		return "tile." + this.unlocalizedName + ".name";
	}

	/**
	 * @return The name of the tile converted to the proper language
	 */
	public String getLocalizedName() {
		return I18n.format(this.getUnlocalizedName());
	}

	/**
	 * @return Whether or not this tile should render
	 */
	public boolean shouldRender() {
		return shouldRender;
	}

	/**
	 * Gets this tile object as a String object.
	 */
	@Override
	public String toString() {
		return "Tile[" + this.getLocalizedName() + "/" + this.getUnlocalizedName() + ":" + this.getRegistryName() + "]";
	}
}
