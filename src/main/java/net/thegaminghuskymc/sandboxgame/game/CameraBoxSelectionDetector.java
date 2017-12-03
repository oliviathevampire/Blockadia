package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.graph.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Collection;

public class CameraBoxSelectionDetector {

    private final Vector3f max;

    private final Vector3f min;

    private final Vector2f nearFar;

    private Vector3f dir;

    public CameraBoxSelectionDetector() {
        dir = new Vector3f();
        min = new Vector3f();
        max = new Vector3f();
        nearFar = new Vector2f();
    }

    public void selectGameItem(Collection<Block> gameItems, Camera camera) {
        dir = camera.getViewMatrix().positiveZ(dir).negate();
        selectGameItem(gameItems, camera.getPosition(), dir);
    }
    
    protected boolean selectGameItem(Collection<Block> gameItems, Vector3f center, Vector3f dir) {
        boolean selected = false;
        Block selectedGameItem = null;
        float closestDistance = Float.POSITIVE_INFINITY;
        return selected;
    }
}
