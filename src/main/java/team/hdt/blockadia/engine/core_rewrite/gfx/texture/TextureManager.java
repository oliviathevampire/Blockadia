package team.hdt.blockadia.engine.core_rewrite.gfx.texture;

import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.util.Loader;
import team.hdt.blockadia.engine.core_rewrite.util.LoadingUtils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class TextureManager {

	public static final Identifier MISSING_TEXTURE_LOCATION = new Identifier("missingno");

	@Nonnull
	private Identifier boundTextureLocation;
	private Map<String, ITexture> textures;

	public TextureManager() {
		textures = new HashMap<>();
		this.load(MISSING_TEXTURE_LOCATION, Loader.loadTexture(LoadingUtils.createMissingImage(256, 256)));
		boundTextureLocation = MISSING_TEXTURE_LOCATION;
	}

	/**
	 * Loads a texture up from a resource location.
	 * 
	 * @param location
	 * 			The location to load the texture from.
	 * 
	 * @param texture
	 * 			The texture to load.
	 */
	public void loadTexture(Identifier location, ITexture texture) {
		textures.put(location.toString(), texture);
	}

	/**
	 * Binds a texture to a resource location.
	 * 
	 * @param location
	 * 			The resource location to bind the texture to.
	 */
	public void bind(Identifier location) {
		if (location == null)
			return;
		if (textures.get(location.toString()) == null) {
			textures.put(location.toString(), Loader.loadTexture(location));
		}
		this.boundTextureLocation = location;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures.get(location.toString()).getId());
	}

	/**
	 * Gets a texture from a resource location.
	 * 
	 * @param location
	 * 			The resource location to get the texture from.
	 * 
	 * @return The texture retrieved from the resource location.
	 */
	public ITexture getTexture(Identifier location) {
		if (location == null)
			location = MISSING_TEXTURE_LOCATION;
		bind(location);
		return textures.get(location.toString());
	}

	public Identifier getBoundTextureLocation() {
		return boundTextureLocation;
	}

	/**
	 * Loads a texture into the texture manager from a resource location.
	 * 
	 * @param location
	 * 			The resource location to load the texture from.
	 * 
	 * @param texture
	 * 			The texture to load.
	 */
	public void load(Identifier location, ITexture texture) {
		textures.put(location.toString(), texture);
	}
}