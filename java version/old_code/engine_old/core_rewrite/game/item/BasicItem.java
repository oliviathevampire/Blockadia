package team.hdt.blockadia.engine.core_rewrite.game.item;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

public class BasicItem extends Item {

	public static final Identifier ITEM_ATLAS = new Identifier("textures/items.png");

	private Vector2f textureCoords;

	public BasicItem(Identifier registryName, String unlocalizedName, Vector2f textureCoords) {
		super(registryName, unlocalizedName);
		this.textureCoords = textureCoords;
	}

	@Override
	public Vector2f getTextureCoords(int layer) {
		return layer == 0 ? textureCoords : null;
	}

	@Override
	public Identifier getTexture() {
		return ITEM_ATLAS;
	}
	
	@Override
	public int getTextureWidth() {
		return 16;
	}
}