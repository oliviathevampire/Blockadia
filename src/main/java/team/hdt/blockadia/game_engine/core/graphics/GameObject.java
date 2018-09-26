package team.hdt.blockadia.game_engine.core.graphics;

import team.hdt.blockadia.game_engine.core.util.math.Transform;

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