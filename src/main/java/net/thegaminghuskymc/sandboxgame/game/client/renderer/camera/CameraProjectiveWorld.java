package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;

public abstract class CameraProjectiveWorld extends CameraProjective implements RaycastingCallback {

    /**
     * the world
     */
    private World world;

    /**
     * looking block informations
     */
    private Vector3f lookCube;
    private Vector3f lookFace;

    /**
     * the terrain index the camera is inside
     */
    private Vector3i world_index;

    public CameraProjectiveWorld(GLFWWindow window) {
        super(window);

        this.lookCube = new Vector3f();
        this.lookFace = new Vector3f();

        this.world_index = new Vector3i();
    }

    @Override
    public void update() {
        super.update();
        if (this.getWorld() == null) {
            return;
        }
        this.getWorld().getTerrainIndex(this.getPosition(), this.world_index);
        Raycasting.raycast(this.getPosition(), this.getViewVector(), 128.0f, this);
    }

    @Override
    public boolean onRaycastCoordinates(int x, int y, int z, Vector3i face) {
        if (this.world == null) {
            return (true);
        }
        Block block = this.world.getBlock(x, y, z);
        if (block.isVisible() && !block.bypassRaycast()) {
            this.lookCube.set(x + face.x, y + face.y, z + face.z);
            this.lookFace.set(face);
            return (true);
        }
        return (false);
    }

    public World getWorld() {
        return (this.world);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setBlock(Block block, Vector3f pos) {

        if (this.getWorld() == null) {
            return;
        }

        Terrain terrain = this.getWorld().setBlock(block, pos.x, pos.y, pos.z);

        if (terrain == null) {
            return;
        }

        terrain.requestFaceVisibilityUpdate();
    }

    public Vector3f getLookCoords() {
        return (this.lookCube);
    }
}
