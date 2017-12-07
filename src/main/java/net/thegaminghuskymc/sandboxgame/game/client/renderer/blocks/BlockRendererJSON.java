package net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMeshTriangle;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMeshVertex;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMesher;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.flat.BlockFace;

import java.util.ArrayList;

/**
 * the default cube renderer
 */
public class BlockRendererJSON extends BlockRenderer {

    private final ArrayList<TerrainMeshTriangle> triangles;

    public BlockRendererJSON(String filepath) {
        super();
        this.triangles = new ArrayList<>();
    }

    @Override
    public void generateBlockVertices(TerrainMesher terrainMesher, Terrain terrain, Block block, int x, int y, int z,
                                      BlockFace[][][][] faces, ArrayList<TerrainMeshTriangle> stack) {
        for (TerrainMeshTriangle triangle : this.triangles) {
            TerrainMeshTriangle clone = (TerrainMeshTriangle) triangle.clone();
            this.offsetVertex(clone.v0, x, y, z);
            this.offsetVertex(clone.v1, x, y, z);
            this.offsetVertex(clone.v2, x, y, z);
            stack.add(clone);
        }
    }

    private void offsetVertex(TerrainMeshVertex v, int x, int y, int z) {
        // offset position
        v.posx = (v.posx + x) * Terrain.BLOCK_SIZE;
        v.posy = (v.posy + y) * Terrain.BLOCK_SIZE;
        v.posz = (v.posz + z) * Terrain.BLOCK_SIZE;
    }

    @Override
    public int getDefaultTextureID(int faceID) {
        return (0);
    }
}
