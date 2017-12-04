package net.thegaminghuskymc.sandboxgame.engine.world;


import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.block.instance.BlockInstance;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public abstract class WorldTerrainStorage extends WorldStorage {

    public WorldTerrainStorage(World world) {
        super(world);
    }

    /**
     * get the terrain index of the given world position
     */
    public abstract Vector3i getIndex(float x, float y, float z, Vector3i dst);

    public final Vector3i getIndex(Vector3f pos, Vector3i dst) {
        return (this.getIndex(pos.x, pos.y, pos.z, dst));
    }

    /**
     * set the block durabiltiy at the given world relative position
     */
    public abstract void setBlockDurability(byte durability, float x, float y, float z);

    /**
     * world position
     */
    public final Block getBlock(Vector3f pos) {
        return (this.getBlock(pos.x, pos.y, pos.z));
    }

    protected abstract Block getBlock(float x, float y, float z);

    /**
     * get the block light at the given world relative position
     */
    public abstract byte getBlockLight(float x, float y, float z);

    /**
     * world position
     */
    public abstract byte getBlockLight(Vector3f pos);

    /**
     * set a block, world coordinates, return the terrain on which the block was
     * set
     */
    public abstract Terrain setBlock(Block block, float x, float y, float z);

    /**
     * total number of terrains
     */
    public abstract int getTerrainCount();

    /**
     * block instance
     */
    public abstract BlockInstance getBlockInstance(float x, float y, float z);

    /**
     * true if the terrain is loaded
     */
    public abstract boolean isLoaded(Terrain terrain);

    /**
     * return the terrain with the given location, or null if the terrain doesnt
     * exists / is empty
     */
    public abstract Terrain get(int x, int y, int z);

    public Terrain get(Vector3i pos) {
        return (this.get(pos.x, pos.y, pos.z));
    }

    /**
     * get the loaded terrains
     */
    public abstract Terrain[] getLoaded();

    /**
     * return true if the terrain can be spawned
     */
    public abstract boolean canHold(Terrain terrain);

    /**
     * add the terrain to the storage, return the terrain it if added
     * sucessfully, return null else way
     */
    public abstract Terrain add(Terrain terrain);

    /**
     * remove the terrain
     */
    public abstract void remove(Terrain terrain);

    /**
     * remove the terrain
     */
    public final void remove(Vector3i index) {
        this.remove(this.get(index));
    }

    @Override
    public void delete() {
        Terrain[] terrains = this.get();
        for (Terrain terrain : terrains) {
            this.remove(terrain);
        }
        this.onDeleted();
    }

    protected void onDeleted() {
    }

    /**
     * return every added terrains
     */
    public abstract Terrain[] get();
}
