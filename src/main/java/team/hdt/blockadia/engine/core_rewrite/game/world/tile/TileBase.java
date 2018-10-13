package team.hdt.blockadia.engine.core_rewrite.game.world.tile;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

public class TileBase extends Tile {

	public static final Identifier TILE_ATLAS = new Identifier("textures/tiles.png");

	private Vector2f textureCoords;

	public TileBase(Identifier registryName, String unlocalizedName, Vector2f textureCoords) {
		super(registryName, unlocalizedName);
		this.textureCoords = textureCoords;
	}

	@Override
	public void update() {
	}

	@Override
	public Vector2f getTextureCoords(int layer) {
		return layer == 0 ? textureCoords : null;
	}

	@Override
	public Identifier getTexture() {
		return TILE_ATLAS;
	}

	@Override
	public int getTextureWidth() {
		return 16;
	}
}
