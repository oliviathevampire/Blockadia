package net.thegaminghuskymc.sandboxgame.engine.entities;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Entity {

    private boolean lookedOn;

    private boolean isAgressive;

    private Vector3f position;

    private Quaternionf rotation;

    private String name;

    public Entity() {
        lookedOn = false;
        isAgressive = false;
        position = new Vector3f(0, 0, 0);
        rotation = new Quaternionf();
    }

    public Entity(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Vector3f getPosition() {
        return position;
    }

    public boolean isLookedOn() {
        return lookedOn;
    }

    public boolean isAgressive() {
        return isAgressive;
    }

    public final void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public final void setRotation(Quaternionf q) {
        this.rotation.set(q);
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public void update() {}

    public void setLookedOn(boolean lookedOn) {
        this.lookedOn = lookedOn;
    }

    public void setIsAgressive(boolean isAgressive) {
        this.isAgressive = isAgressive;
    }

}
