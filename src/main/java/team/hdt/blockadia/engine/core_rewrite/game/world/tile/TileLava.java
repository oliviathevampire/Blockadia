package team.hdt.blockadia.engine.core_rewrite.game.world.tile;

import com.google.common.collect.Maps;
import org.joml.Vector2f;
import org.joml.Vector4f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.game.world.map.TileMap;
import team.hdt.blockadia.engine.core_rewrite.gfx.light.Light;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.TileRenderer;

import java.util.Map;

public class TileLava extends TileBase {

	private static final Map<String, Light> LIGHT_CACHE = Maps.<String, Light>newHashMap();

	public TileLava(Identifier registryName, String unlocalizedName, Vector2f textureCoords) {
		super(registryName, unlocalizedName, textureCoords);
	}

	@Override
	public void render(float x, float y, MasterRenderer renderer, TileRenderer tileRenderer) {
		renderer.renderLights(this.getLight(x, y));
	}

	@Override
	public void onTileDestroyed(TileMap map, float x, float y) {
		LIGHT_CACHE.remove(x + "," + y);
	}

	/**
	 * Gets the light object at the specified position.
	 * 
	 * @param x
	 *            The x position to get the light.
	 * 
	 * @param y
	 *            The y position to get the light.
	 * 
	 * @return The light object from the given tile position.
	 */
	private Light getLight(float x, float y) {
		Light light = LIGHT_CACHE.get(x + "," + y);
		if (light == null) {
			light = new Light(new Vector2f(x + 8, y + 8), new Vector4f(0.8f, 0.2f, 0.0f, 10.0f), 20);
			LIGHT_CACHE.put(x + "," + y, light);
		}
		return light;
	}
}