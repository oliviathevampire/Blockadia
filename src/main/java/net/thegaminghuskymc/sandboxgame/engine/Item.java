package net.thegaminghuskymc.sandboxgame.engine;

import net.thegaminghuskymc.sandboxgame.engine.graph.Mesh;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Item {

    private boolean held;

    private Mesh[] meshes;

    private final Vector3f position;

    private float scale;

    private final Quaternionf rotation;

    private int textPos;
    
    private boolean disableFrustumCulling;

    private boolean insideFrustum;

    public Item() {
        held = false;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Quaternionf();
        textPos = 0;
        insideFrustum = true;
        disableFrustumCulling = false;
    }

    public Item(Mesh mesh) {
        this();
        this.meshes = new Mesh[]{mesh};
    }

    public Item(Block block) {
        this();
    }

    public Item(Mesh[] meshes) {
        this();
        this.meshes = meshes;
    }

    public Item getItemFromBlock(Block block) {
        return new Item(block);
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getTextPos() {
        return textPos;
    }

    public boolean isHeld() {
        return held;
    }

    public final void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public final void setScale(float scale) {
        this.scale = scale;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public final void setRotation(Quaternionf q) {
        this.rotation.set(q);
    }

    public Mesh getMesh() {
        return meshes[0];
    }

    public Mesh[] getMeshes() {
        return meshes;
    }

    public void setMeshes(Mesh[] meshes) {
        this.meshes = meshes;
    }

    public void setMesh(Mesh mesh) {
        this.meshes = new Mesh[]{mesh};
    }

    public void cleanup() {
        int numMeshes = this.meshes != null ? this.meshes.length : 0;
        for (int i = 0; i < numMeshes; i++) {
            this.meshes[i].cleanUp();
        }
    }

    public void setIsHeld(boolean isHeld) {
        this.held = isHeld;
    }

    public void setTextPos(int textPos) {
        this.textPos = textPos;
    }

    public boolean isInsideFrustum() {
        return insideFrustum;
    }

    public void setInsideFrustum(boolean insideFrustum) {
        this.insideFrustum = insideFrustum;
    }
    
    public boolean isDisableFrustumCulling() {
        return disableFrustumCulling;
    }

    public void setDisableFrustumCulling(boolean disableFrustumCulling) {
        this.disableFrustumCulling = disableFrustumCulling;
    }    
}
