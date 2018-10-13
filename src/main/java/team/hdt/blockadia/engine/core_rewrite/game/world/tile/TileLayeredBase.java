package team.hdt.blockadia.engine.core_rewrite.game.world.tile;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

public class TileLayeredBase extends TileBase {

	private Vector2f overlayTextureCoords;

	public TileLayeredBase(Identifier registryName, String unlocalizedName, Vector2f textureCoords, Vector2f overlayTextureCoords) {
		super(registryName, unlocalizedName, textureCoords);
		this.overlayTextureCoords = overlayTextureCoords;
	}

	@Override
	public Vector2f getTextureCoords(int layer) {
		return layer == 1 ? overlayTextureCoords : super.getTextureCoords(layer);
	}
}
