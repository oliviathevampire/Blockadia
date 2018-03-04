package net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.faces.Face;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public class BlockRendererLeaves extends BlockRendererCube {

	public BlockRendererLeaves(int textureID) {
		super(textureID);
	}

	@Override
	protected boolean canRenderFace(Terrain terrain, Block block, Face face, int x, int y, int z) {
		Vector3i vec = face.getVector();
		// get the neighbor of this face
		Block neighbor = terrain.getBlock(x + vec.x, y + vec.y, z + vec.z);

		return (!neighbor.isVisible());
	}

}
