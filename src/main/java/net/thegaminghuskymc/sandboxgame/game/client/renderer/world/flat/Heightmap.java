package net.thegaminghuskymc.sandboxgame.game.client.renderer.world.flat;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;

//TODO dont need heightmaps yet, but thinkign about implemeneting it
public class Heightmap {

	private Matrix4f matrix;
	private final Vector3f pos;
	private final Vector3f rot;
	private final Vector3f scale;

	private int width;
	private int depth;

	public Heightmap() {
		this.matrix = new Matrix4f();
		this.pos = new Vector3f();
		this.rot = new Vector3f();
		this.scale = new Vector3f();
	}

	public int getWidth() {
		return (this.width);
	}

	public int getDepth() {
		return (this.depth);
	}

	public Matrix4f getTransformationMatrix() {
		return (this.matrix);
	}
}
