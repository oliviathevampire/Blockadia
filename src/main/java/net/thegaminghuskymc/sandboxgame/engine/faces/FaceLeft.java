package net.thegaminghuskymc.sandboxgame.engine.faces;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public class FaceLeft implements Face {
    private static Vector3i vec = new Vector3i(0, 0, -1);
    private static Vector3f normal = new Vector3f(0, 0, -1);
    private static Vector3i movement = new Vector3i(1, 1, 0);

    @Override
    public String getName() {
        return ("LEFT");
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
        return (Face.get(Face.RIGHT));
    }

    @Override
    public int getID() {
        return (Face.LEFT);
    }

    @Override
    public float getFaceFactor() {
        return (0.70f);
    }

    @Override
    public Vector3i getAllowedTranslation() {
        return (movement);
    }
}
