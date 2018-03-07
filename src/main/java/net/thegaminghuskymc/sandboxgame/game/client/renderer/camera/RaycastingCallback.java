package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public interface RaycastingCallback {
    /**
     * return true if the raytracing should stop
     */
    public boolean onRaycastCoordinates(int x, int y, int z, Vector3i face);

}