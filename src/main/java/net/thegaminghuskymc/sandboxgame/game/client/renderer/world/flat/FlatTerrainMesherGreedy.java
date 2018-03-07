/**
 * *	This file is part of the project https://github.com/toss-dev/VoxelEngine
 * *
 * *	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
 * *
 * *	PEREIRA Romain
 * *                                       4-----7
 * *                                      /|    /|
 * *                                     0-----3 |
 * *                                     | 5___|_6
 * *                                     |/    | /
 * *                                     1-----2
 */

package net.thegaminghuskymc.sandboxgame.game.client.renderer.world.flat;

import net.thegaminghuskymc.sandboxgame.engine.faces.Face;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks.BlockRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMeshTriangle;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMeshVertex;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMesher;

import java.util.ArrayList;


/** an object which is used to generate terrain meshes dynamically */
public class FlatTerrainMesherGreedy extends TerrainMesher {

    /**
     * fill an array of dimension
     * [Terrain.BLOCK_SIZEIZE_X][Terrain.BLOCK_SIZEIZE_Y][Terrain.
     * BLOCK_SIZEIZE_Z][6] of terrain faces visibility
     */
    @Override
    protected void fillVertexStacks(Terrain terrain, ArrayList<TerrainMeshTriangle> opaqueStack,
                                    ArrayList<TerrainMeshTriangle> transparentStack) {
        // get visibile faces
        BlockFace[][][][] faces = this.getFacesVisibility(terrain, opaqueStack, transparentStack);
        if (faces == null) {
            return;
        }

        // for each face
        for (int faceID = 0; faceID < Face.faces.length; faceID++) {
            boolean[][][] visited = new boolean[Terrain.DIMX][Terrain.DIMY][Terrain.DIMZ];

            for (int z = 0; z < Terrain.DIMZ; z++) {
                for (int y = 0; y < Terrain.DIMY; y++) {
                    for (int x = 0; x < Terrain.DIMX; x++) {

                        if (visited[x][y][z]) {
                            continue;
                        }

                        BlockFace face = faces[faceID][x][y][z];

                        // the face isnt visible, or has already been processed
                        if (face == null) {
                            continue;
                        }

                        ArrayList<TerrainMeshTriangle> stack = face.getBlock().isOpaque() ? opaqueStack
                                : transparentStack;

                        // TOP OR BOT FACE
                        if (faceID == Face.TOP || faceID == Face.BOT) {

                            int width = 1;

                            // generate the rectangle width
                            while (x + width < Terrain.DIMX && !visited[x + width][y][z]
                                    && face.equals(faces[faceID][x + width][y][z])) {
                                visited[x + width][y][z] = true;
                                ++width;
                            }

                            // generate the rectangle height
                            int depth = 1;
                            depth_test:
                            while (y + depth < Terrain.DIMY) {
                                for (int dx = 0; dx < width; dx++) {
                                    if (visited[x + dx][y + depth][z]
                                            || !face.equals(faces[faceID][x + dx][y + depth][z])) {
                                        break depth_test;
                                    }
                                    visited[x + dx][y + depth - 1][z] = true;
                                }
                                ++depth;
                            }

                            // now we have the rectangle width / height
                            for (int i = 0; i < 4; i++) {
                                Vector3i offset = BlockRenderer.VERTICES[BlockRenderer.FACES_VERTICES[faceID][i]];
                                TerrainMeshVertex vertex = face.vertices[i];

                                vertex.posx = x + offset.x * width;
                                vertex.posy = y + offset.y * depth;

                                vertex.u *= width;
                                vertex.v *= depth;
                            }
                            face.pushVertices(stack);

                            // pass visited blocks
                            x += width - 1;
                        }

                        // RIGHT OR LEFT FACE
                        else if (faceID == Face.RIGHT || faceID == Face.LEFT) {
                            int width = 1;

                            // generate the rectangle width
                            while (x + width < Terrain.DIMX && !visited[x + width][y][z]
                                    && face.equals(faces[faceID][x + width][y][z])) {
                                visited[x + width][y][z] = true;
                                ++width;
                            }

                            // generate the rectangle height
                            int height = 1;
                            height_test:
                            while (z + height < Terrain.DIMZ) {
                                for (int dx = 0; dx < width; dx++) {
                                    if (visited[x + dx][y][z + height]
                                            || !face.equals(faces[faceID][x + dx][y][z + height])) {
                                        break height_test;
                                    }
                                    visited[x + dx][y][z + height - 1] = true;
                                }
                                ++height;
                            }

                            // now we have the rectangle width / height
                            for (int i = 0; i < 4; i++) {
                                Vector3i offset = BlockRenderer.VERTICES[BlockRenderer.FACES_VERTICES[faceID][i]];
                                TerrainMeshVertex vertex = face.vertices[i];

                                vertex.posx = x + offset.x * width;
                                vertex.posz = z + offset.z * height;

                                vertex.u *= width;
                                vertex.v *= height;
                            }
                            face.pushVertices(stack);

                            // pass visited blocks
                            x += width - 1;
                        }

                        // ELSE : FRONT OR BACK
                        else if (faceID == Face.FRONT || faceID == Face.BACK) {

                            int depth = 1;

                            // generate the rectangle width
                            while (y + depth < Terrain.DIMY && !visited[x][y + depth][z]
                                    && face.equals(faces[faceID][x][y + depth][z])) {
                                visited[x][y + depth][z] = true;
                                ++depth;
                            }

                            // generate the rectangle depth
                            int height = 1;
                            height_test:
                            while (z + height < Terrain.DIMZ) {
                                for (int dy = 0; dy < depth; dy++) {
                                    if (visited[x][y + dy][z + height]
                                            || !face.equals(faces[faceID][x][y + dy][z + height])) {
                                        break height_test;
                                    }
                                    visited[x][y + dy][z + height - 1] = true;
                                }
                                ++height;
                            }

                            // now we have the rectangle width / height
                            for (int i = 0; i < 4; i++) {
                                Vector3i offset = BlockRenderer.VERTICES[BlockRenderer.FACES_VERTICES[faceID][i]];

                                TerrainMeshVertex vertex = face.vertices[i];

                                vertex.posy = y + offset.y * depth;
                                vertex.posz = z + offset.z * height;

                                vertex.u *= depth;
                                vertex.v *= height;
                            }
                            face.pushVertices(stack);
                        }
                    }
                }
            }
        }
    }
}