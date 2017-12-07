package net.thegaminghuskymc.sandboxgame.engine.world;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.block.instance.BlockInstance;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntityStorage;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.PhysicObject;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.PhysicObjectBlock;
import net.thegaminghuskymc.sandboxgame.engine.world.generator.WorldGenerator;
import net.thegaminghuskymc.sandboxgame.engine.world.generator.WorldGeneratorEmpty;

import java.util.ArrayList;
import java.util.Random;

/**
 * TODO Main world class, may change to a "Planet" class, and a new World class
 * should be build, and load multiple "Planets"
 */
public abstract class World implements Taskable {

    private static final int seed = 42;
    public static final SimplexNoiseOctave NOISE_OCTAVE = new SimplexNoiseOctave(seed);
    public static final SimplexNoiseOctave[] NOISE_OCTAVES = {new SimplexNoiseOctave(seed + 1),
            new SimplexNoiseOctave(seed + 2), new SimplexNoiseOctave(seed + 3), new SimplexNoiseOctave(seed + 4)};
    /**
     * every loaded terrain are in
     */
    private final WorldTerrainStorage terrains;
    /**
     * every world entities.
     */
    private final WorldEntityStorage entities;
    /**
     * rng
     */
    private final Random rng;
    /**
     * the world generator
     */
    private WorldGenerator generator;
    /**
     * number of updates which was made for the world
     */
    private long tick;

    public World() {
        this.terrains = this.instanciateTerrainStorage();
        this.entities = new WorldEntityStorage(this);
        this.rng = new Random();
        this.tick = 0;
        this.setWorldGenerator(new WorldGeneratorEmpty());
    }

    /**
     * save the given world to the given folder
     */

    public static void save(World world, String filepath) {
    }

    /**
     * load the given folder as a world
     */
    public static World load(String filepath) {
        return (null);
    }

    /**
     * create the terrain storage of this world (can be null)
     */
    protected abstract WorldTerrainStorage instanciateTerrainStorage();

    /**
     * tasks to be run to update the world
     */
    @Override
    public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {
        this.entities.getTasks(engine, tasks);
        this.terrains.getTasks(engine, tasks);
        this.onTasksGet(engine, tasks);
        this.tick();
    }

    /**
     * call back to add tasks to run
     */
    protected void onTasksGet(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {
    }

    /**
     * generate the terrain for the given coordinates, spawn it if un-existant
     */
    protected Terrain generateTerrain(int x, int y, int z) {
        Terrain terrain = this.getTerrain(x, y, z);
        if (terrain == null) {
            terrain = new Terrain(x, y, z);
            this.spawnTerrain(terrain);
        }
        return (this.generateTerrain(terrain));
    }

    protected Terrain generateTerrain(Terrain terrain) {
        terrain.preGenerated();
        this.generator.generate(terrain);
        terrain.postGenerated();
        return (terrain);
    }

    /**
     * set the world generator
     */
    protected void setWorldGenerator(WorldGenerator worldgen) {
        this.generator = worldgen;
    }

    /**
     * get the rng
     */
    public final Random getRNG() {
        return (this.rng);
    }

    /**
     * delete the world : de-allocate every allocated memory
     */
    public final void delete() {
        this.entities.delete();
        this.terrains.delete();
        this.onDelete();
    }

    private void onDelete() {

    }

    /**
     * return the terrain with the given location, or null if the terrain doesnt
     * exists / is empty
     */
    public WorldTerrainStorage getTerrainStorage() {
        return (this.terrains);
    }

    public WorldEntityStorage getEntityStorage() {
        return (this.entities);
    }

    /**
     * get the block at the given world relative position
     */
    public Block getBlock(float x, float y, float z) {
        return (this.terrains.getBlock(x, y, z));
    }

    /**
     * world position
     */
    public Block getBlock(Vector3f pos) {
        return (this.terrains.getBlock(pos.x, pos.y, pos.z));
    }

    /**
     * set the block at the given world coordinates
     */
    public Terrain setBlock(Block block, float x, float y, float z) {
        return (this.terrains.setBlock(block, x, y, z));
    }

    public byte getBlockLight(Vector3f pos) {
        return (this.terrains.getBlockLight(pos.x, pos.y, pos.z));
    }

    /**
     * return the terrain at the given index
     */
    public Terrain getTerrain(int x, int y, int z) {
        return (this.terrains.get(x, y, z));
    }

    /**
     * return the terrain at the given index
     */
    public Terrain getTerrain(Vector3i pos) {
        return (this.terrains.get(pos));
    }

    /**
     * return true if the given terrain is loaded
     */
    public boolean isTerrainLoaded(Terrain terrain) {
        return (this.terrains.isLoaded(terrain));
    }

    /**
     * get every loaded terrains
     */
    public Terrain[] getLoadedTerrains() {
        return (this.terrains.getLoaded());
    }

    /**
     * get the terrain index for the given world position
     */
    public Vector3i getTerrainIndex(Vector3f position) {
        return (this.getTerrainIndex(position, new Vector3i()));
    }

    public Vector3i getTerrainIndex(Vector3f position, Vector3i world_index) {
        return (this.terrains.getIndex(position, world_index));
    }

    /**
     * spawn a terrain
     */
    public final Terrain spawnTerrain(Terrain terrain) {
        return (this.terrains.add(terrain));
    }

    /**
     * spawn an entity into the world
     */
    public final Entity spawnEntity(Entity entity) {
        return (this.entities.spawn(entity));
    }

    /**
     * tick the world once
     */
    public void tick() {
        this.tick++;
    }

    public long getTick() {
        return (this.tick);
    }

    @Override
    public String toString() {
        return ("World: " + this.getName());
    }
    // TODO: save and load

    /**
     * return world name
     */
    public abstract String getName();

    /**
     * world location
     */
    public BlockInstance getBlockInstance(float x, float y, float z) {
        return (this.terrains.getBlockInstance(x, y, z));
    }

    public final void load() {
        this.onLoaded();
    }

    /**
     * called when this world is loaded
     */
    protected void onLoaded() {

    }

    public final void unload() {
        this.onUnloaded();
    }

    private void onUnloaded() {
    }

    /**
     * get the PhysicObjects (blocks and entities) colliding with the
     * PhysicObject
     *
     * @return : the physic object list
     */
    public final ArrayList<PhysicObject> getCollidingPhysicObjects(PhysicObject exclude, float minx, float miny,
                                                                   float minz, float maxx, float maxy, float maxz) {
        ArrayList<PhysicObject> lst = new ArrayList<PhysicObject>();

        int mx = Maths.floor(minx);
        int Mx = Maths.ceil(maxx);
        int my = Maths.floor(miny);
        int My = Maths.ceil(maxy);
        int mz = Maths.floor(minz);
        int Mz = Maths.ceil(maxz);

        // iterate though each blocks
        for (int x = mx; x < Mx; x++) {
            for (int y = my; y < My; y++) {
                for (int z = mz; z < Mz; z++) {
                    Block block = this.getBlock(x, y, z);
                    if (!block.isCrossable()) {
                        lst.add(new PhysicObjectBlock(block, x, y, z));
                    }
                }
            }
        }
        return (lst);
    }
}
