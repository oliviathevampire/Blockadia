package net.thegaminghuskymc.sandboxgame.engine.faces;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public class FaceTop implements Face {
	private static Vector3i vec = new Vector3i(0, 1, 0);
	private static Vector3f normal = new Vector3f(0, 1, 0);
	private static Vector3i movement = new Vector3i(1, 0, 1);

	@Override
	public String getName() {
		return ("TOP");
	}

	@Override
	public Vector3f getNormal() {
		return (normal);
	}

	@Override
	public Vector3i getVector() {
		return (vec);
	}

	@Override
	public Face getOpposite() {
		return (Face.get(Face.BOT));
	}

	@Override
	public int getID() {
		return (Face.TOP);
	}

	@Override
	public float getFaceFactor() {
		return (1.0f);
	}
	
	@Override
	public Vector3i getAllowedTranslation() {
		return (movement);
	}
}
