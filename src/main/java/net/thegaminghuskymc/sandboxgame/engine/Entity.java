package net.thegaminghuskymc.sandboxgame.engine;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.thegaminghuskymc.sandboxgame.engine.graph.Mesh;
import net.thegaminghuskymc.sandboxgame.engine.util.math.AxisAlignedBB;
import net.thegaminghuskymc.sandboxgame.game.DummyGame;

public class Entity {

    private boolean lookedOn;

    private boolean isAgressive;

    private Mesh[] meshes;

    private final Vector3f position;

    private float scale;

    private final Quaternionf rotation;

    private int textPos;
    
    public AxisAlignedBB boundingbox;

    public float width = 1f;
    public float height = 1f;
    
    public Entity() {
        lookedOn = false;
        isAgressive = false;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Quaternionf();
        textPos = 0;
    }

    public Entity(Mesh mesh) {
        this();
        this.meshes = new Mesh[]{mesh};
    }

    public Entity(Mesh[] meshes) {
        this();
        this.meshes = meshes;
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getTextPos() {
        return textPos;
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
		if (offsetZ != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
		}
		if (offsetX != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
			position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
		}
		position.y += offsetY;
		boundingbox = new AxisAlignedBB(position.x, position.y, position.z, position.x + width, position.y + height,
				position.z + width);
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

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
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

    public void update() {
        if (getAffectedByGravity() && !DummyGame.getGame().world.isCollidingWithGround(this)) position.y -= DummyGame.GRAVITY;
    }

    public void setLookedOn(boolean lookedOn) {
        this.lookedOn = lookedOn;
    }

    public void setIsAgressive(boolean isAgressive) {
        this.isAgressive = isAgressive;
    }

    public void setTextPos(int textPos) {
        this.textPos = textPos;
    }
    
    public boolean getAffectedByGravity() {
    	return true;
    }
    
}
