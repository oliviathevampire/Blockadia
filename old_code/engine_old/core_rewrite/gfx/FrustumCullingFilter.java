package team.hdt.blockadia.engine.core_rewrite.gfx;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import team.hdt.blockadia.engine.core_rewrite.game.entity.Entity;
import team.hdt.blockadia.engine.core_rewrite.game.world.map.Chunk;
import team.hdt.blockadia.engine.core_rewrite.game.world.map.TileMap;
import team.hdt.blockadia.engine.core_rewrite.game.world.tile.TileEntry;
import team.hdt.blockadia.engine.core_rewrite.three_d.AxisAlignedBB;

import java.util.List;

public class FrustumCullingFilter {

	private Matrix4f projectionViewMatrix;
	private FrustumIntersection frustumIntersection;
	private FrustumIntersection tileFrustumIntersection;

	public FrustumCullingFilter() {
		this.projectionViewMatrix = new Matrix4f();
		this.frustumIntersection = new FrustumIntersection();
		this.tileFrustumIntersection = new FrustumIntersection();
	}

	public void updateFrustum(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		this.projectionViewMatrix.set(projectionMatrix);
		this.projectionViewMatrix.mul(viewMatrix);
		this.frustumIntersection.set(projectionViewMatrix);
		this.tileFrustumIntersection.set(projectionViewMatrix.scale(1.2f));
	}

	public boolean insideFrustum(float x, float y, float z, float boundingRadius) {
		return frustumIntersection.testSphere(x, y, z, boundingRadius);
	}

	public boolean insideFrustum(float x, float y, AxisAlignedBB box) {
		return frustumIntersection.testAab((float) (x + box.getX()), (float) (y + box.getY()), 0, (float) (x + box.getXMax()), (float) (y + box.getYMax()), 1);
	}

	public void filterTiles(List<TileEntry> tiles) {
		for (TileEntry tile : tiles) {
			tile.setRemoved(!insideFrustum(tile.getX() + 8, tile.getY() + 8, 0, 64));
		}
	}

	public void filterChunks(List<Chunk> chunks) {
		for (Chunk chunk : chunks) {
			float x = chunk.getGridX() * TileMap.CHUNK_SIZE * 16;
			float y = chunk.getGridY() * TileMap.CHUNK_SIZE * 16;
			chunk.setOffScreen(!tileFrustumIntersection.testSphere(x, y, 0, TileMap.CHUNK_SIZE * 16));
		}
	}

	public void filterEntities(List<Entity> entities) {
		for (Entity entity : entities) {
			entity.setInsideFrustum(insideFrustum(entity.getX(), entity.getY(), 0, 16 * entity.getScale()));
		}
	}

	// public void filterObjects(List<? extends GameObject> objects, float meshBoundingRadius) {
	// float boundingRadius;
	// Vector3f pos;
	// for (GameObject object : objects) {
	// boundingRadius = object.getScale().y * meshBoundingRadius;
	// pos = object.getPosition();
	// object.setInsideFrustum(insideFrustum(pos.x, pos.y, pos.z, boundingRadius));
	// }
	// }
	//
	// public void filter(Map<? extends ICullableObjectModel, List<GameObject>> objects) {
	// for (Map.Entry<? extends ICullableObjectModel, List<GameObject>> entry : objects.entrySet()) {
	// List<? extends GameObject> gameItems = entry.getValue();
	// filterObjects(gameItems, entry.getKey().getBoundingRadius());
	// }
	// }
}