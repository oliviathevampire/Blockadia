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

package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelMesh;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelMeshVertex;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelSkin;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Stack;


/** an object which is used to generate model meshes dynamically */
public abstract class ModelMesher {

    /** number of block is need to know how to calculate UVs */
    public ModelMesher() {
    }

    public final void generate(EditableModel editableModel) {

        // if empty model
        if (editableModel.getBlockDataCount() == 0) {
            editableModel.getMesh().setVertices(null);
            editableModel.getMesh().setIndices(null);
            return;
        }

        // prepare the mesh vertex stack
        Stack<ModelMeshVertex> vertexStack = new Stack<ModelMeshVertex>();
        Stack<Short> indicesStack = new Stack<Short>();
        HashMap<ModelSkin, BufferedImage> skinsData = new HashMap<ModelSkin, BufferedImage>();

        // do the generation
        this.doGenerate(editableModel, vertexStack, indicesStack, skinsData);

        // stack to buffer
        ByteBuffer vertices = BufferUtils.createByteBuffer(vertexStack.size() * ModelMesh.BYTES_PER_VERTEX);
        for (ModelMeshVertex vertex : vertexStack) {
            vertex.store(vertices);
        }
        vertices.flip();

        // stack to buffer
        ByteBuffer indices = BufferUtils.createByteBuffer(indicesStack.size() * 2);
        for (Short index : indicesStack) {
            indices.putShort(index);
        }
        indices.flip();

        // set vertices
        editableModel.getMesh().setVertices(vertices);
        editableModel.getMesh().setIndices(indices);

        for (ModelSkin modelSkin : editableModel.getSkins()) {
            BufferedImage skin = skinsData.get(modelSkin);
            if (skin == null) {
                Logger.get().log(Logger.Level.WARNING, "ModelMesher didn't generate every ModelSkins BufferedImage: "
                        + skinsData.size() + "/" + editableModel.getSkins().size());
            }
            // may cause error if buffered image has a too high resolution
            modelSkin.getGLTexture().setData(skin);
        }
    }

    protected abstract void doGenerate(EditableModel editableModel, Stack<ModelMeshVertex> vertices,
                                       Stack<Short> indices, HashMap<ModelSkin, BufferedImage> skinsData);
}