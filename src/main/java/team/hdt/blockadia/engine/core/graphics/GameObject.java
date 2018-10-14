package team.hdt.blockadia.engine.core.graphics;

import ga.pheonix.utillib.utils.vectors.Transform;

public abstract class GameObject {

    private Transform transform;
    private int textureID = 1;

    public GameObject(Transform transform) {
        this.transform = transform;
    }

    public abstract void update();

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
}