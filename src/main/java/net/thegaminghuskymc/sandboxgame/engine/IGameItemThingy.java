package net.thegaminghuskymc.sandboxgame.engine;

import net.thegaminghuskymc.sandboxgame.engine.graph.Mesh;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public interface IGameItemThingy {

    Vector3f getPosition();

    void setPosition(float x, float y, float z);

    int getTexturePosition();

    void setTexturePosition(int texturePosition);

    float getScale();

    float setScale(float scale);

    Quaternionf getRotation();

    void setRotation(Quaternionf q);

    Mesh getMesh();

    void setMesh(Mesh mesh);

    Mesh[] getMeshes();

    void setMeshes(Mesh[] meshes);

    void cleanup();

    boolean isInsideFrustum();

    void setInsideFrustum(boolean insideFrustum);

    boolean isDisableFrustumCulling();

    void setDisableFrustumCulling(boolean disableFrustumCulling);

}
