package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelMeshVertex;

public class ModelBlockFace {

    // the vertices
    public final ModelMeshVertex[] vertices;

    public ModelBlockFace(ModelMeshVertex... vertices) {
        this.vertices = vertices;
    }
}