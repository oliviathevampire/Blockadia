package net.thegaminghuskymc.sandboxgame.engine.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntityDespawn;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntitySpawn;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.engine.world.WorldStorage;
import net.thegaminghuskymc.sandboxgame.engine.world.physic.WorldPhysicSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * data structure which contains every entities
 * <p>
 * It aims is to have every entities stored in a continuous memory space and to
 * be able to get sorted entities (by type, so model) fastly, to optimize
 * rendering Insertion and deletion are also optimized
 **/
public class WorldEntityStorage extends WorldStorage {

    /**
     * world physic
     */
    private final WorldPhysicSystem physics;
    /**
     * the entities by their UUID
     */
    private HashMap<Integer, WorldEntity> entities;
    /**
     * the list of the entities sharing the class
     */
    private HashMap<Class<? extends WorldEntity>, ArrayList<WorldEntity>> entitiesByClass;

    public WorldEntityStorage(World world) {
        super(world);
        this.entities = new HashMap<>();
        this.entitiesByClass = new HashMap<>();
        this.physics = new WorldPhysicSystem();
    }

    /**
     * get all entities
     */
    public Collection<WorldEntity> getEntities() {
        return (this.entities.values());
    }

    public WorldEntity getEntity(Integer worldID) {
        return (this.entities.get(worldID));
    }

    /**
     * return a list of entities using the same class
     */
    public ArrayList<WorldEntity> getEntitiesByClass(Class<? extends WorldEntity> clazz) {
        return (this.entitiesByClass.get(clazz));
    }

    public ArrayList<WorldEntity> getEntitiesByClass(WorldEntity entity) {
        return (this.getEntitiesByClass(entity.getClass()));
    }

    /**
     * return a collection of array list of entities, where each array list holds
     * entities of the same class
     */
    public Collection<ArrayList<WorldEntity>> getEntitiesByClass() {
        return (this.entitiesByClass.values());
    }

    /**
     * return the entity with the given world id
     */
    public WorldEntity getEntityByID(Integer id) {
        return (this.entities.get(id));
    }

    /**
     * return true if the entity is already stored, false else way
     */
    public boolean contains(WorldEntity entity) {
        return (this.entities.containsValue(entity));
    }

    public boolean contains(Integer entityID) {
        return (this.entities.containsKey(entityID));
    }

    /**
     * add an entity to the storage
     */
    public WorldEntity spawn(WorldEntity entity) {

        if (this.contains(entity)) {
            Logger.get().log(Logger.Level.WARNING, "Tried to spawn an already spawned entity", entity);
            return (entity);
        }

        // generate entity unique id
        Integer id = this.generateNextEntityID(entity);

        // prepare the entity
        entity.setEntityID(id);

        // add it to the list
        this.entities.put(id, entity);

        // spawn the entity
        entity.setWorld(this.getWorld());
        entity.onSpawn(this.getWorld());

        // add it to the type list
        this.addEntityToTypeList(entity);

        // invoke events
        this.invokeEvent(new EventEntitySpawn(entity));

        return (entity);
    }

    private void addEntityToTypeList(WorldEntity entity) {
        ArrayList<WorldEntity> type_list = this.getEntitiesByClass(entity.getClass());
        if (type_list == null) {
            type_list = new ArrayList<>(1);
            this.entitiesByClass.put(entity.getClass(), type_list);
        }
        type_list.add(entity);
    }

    /**
     * generate unique id for the given entity (assuming the entity isnt already
     * stored
     */
    private Integer generateNextEntityID(WorldEntity entity) {

        // get the entity id if it already has one
        if (entity.getEntityID() != WorldEntity.DEFAULT_ENTITY_ID && !(this.contains(entity.getEntityID()))) {
            return (entity.getEntityID());
        }

        // else generate one
        Integer id = entity.hashCode();
        while (this.contains(id)) {
            ++id;
        }
        return (id);
    }

    /**
     * remove the given entity
     */
    public WorldEntity despawn(WorldEntity entity) {
        if (!(this.contains(entity))) {
            Logger.get().log(Logger.Level.WARNING, "Tried to remove an entity which wasn't in the world");
            return (null);
        }

        // remove from global list
        this.entities.remove(entity.getEntityID());

        // remove from type list
        ArrayList<WorldEntity> type_list = this.getEntitiesByClass(entity);
        if (type_list != null) {
            type_list.remove(entity);
            if (type_list.size() == 0) {
                this.entitiesByClass.remove(entity.getClass());
            }
        }

        // invoke events
        this.invokeEvent(new EventEntityDespawn(entity));

        return (entity);
    }

    /**
     * clean the entity storage, remove every entities
     */
    private void removeAll() {
        this.entities.clear();
        this.entitiesByClass.clear();
    }

    @Override
    public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {

        Collection<WorldEntity> entities_collection = this.getEntities();
        int size = entities_collection.size();
        WorldEntity[] entities = entities_collection.toArray(new WorldEntity[size]);

        tasks.add(engine.new Callable<Taskable>() {

            @Override
            public WorldEntityStorage call() {

                double dt = engine.getTimer().getDt();

                for (WorldEntity entity : entities) {
                    entity.preWorldUpdate(engine.getTimer().getDt());
                }

                physics.update(dt);

                for (WorldEntity entity : entities) {
                    entity.postWorldUpdate(dt);
                }
                return (WorldEntityStorage.this);
            }

            @Override
            public String getName() {
                return ("EntityStorage update");
            }
        });
    }

    @Override
    public void delete() {
        this.removeAll();
    }
}
