package net.thegaminghuskymc.sandboxgame.world.entity;

import net.thegaminghuskymc.sandboxgame.Logger;
import net.thegaminghuskymc.sandboxgame.Taskable;
import net.thegaminghuskymc.sandboxgame.GameEngine;
import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.events.world.entity.EventEntityDespawn;
import net.thegaminghuskymc.sandboxgame.events.world.entity.EventEntitySpawn;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.world.WorldStorage;

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
     * the entities by their UUID
     */
    private HashMap<Integer, Entity> entities;

    /**
     * the list of the entities sharing the class
     */
    private HashMap<Class<? extends Entity>, ArrayList<Entity>> entitiesByClass;

    public WorldEntityStorage(World world) {
        super(world);
        this.entities = new HashMap<Integer, Entity>();
        this.entitiesByClass = new HashMap<Class<? extends Entity>, ArrayList<Entity>>();
    }

    /**
     * get all entities
     */
    public Collection<Entity> getEntities() {
        return (this.entities.values());
    }

    public Entity getEntity(Integer worldID) {
        return (this.entities.get(worldID));
    }

    /**
     * return a list of entities using the same class
     */
    public ArrayList<Entity> getEntitiesByClass(Class<? extends Entity> clazz) {
        return (this.entitiesByClass.get(clazz));
    }

    public ArrayList<Entity> getEntitiesByClass(Entity entity) {
        return (this.getEntitiesByClass(entity.getClass()));
    }

    /**
     * return a collection of array list of entities, where each array list
     * holds entities of the same class
     */
    public Collection<ArrayList<Entity>> getEntitiesByClass() {
        return (this.entitiesByClass.values());
    }

    /**
     * return the entity with the given world id
     */
    public Entity getEntityByID(Integer id) {
        return (this.entities.get(id));
    }

    /**
     * return true if the entity is already stored, false else way
     */
    public boolean contains(Entity entity) {
        return (this.entities.containsValue(entity));
    }

    public boolean contains(Integer entityID) {
        return (this.entities.containsKey(entityID));
    }

    /**
     * add an entity to the storage
     */
    public Entity spawn(Entity entity) {

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

    private void addEntityToTypeList(Entity entity) {
        ArrayList<Entity> type_list = this.getEntitiesByClass(entity.getClass());
        if (type_list == null) {
            type_list = new ArrayList<Entity>(1);
            this.entitiesByClass.put(entity.getClass(), type_list);
        }
        type_list.add(entity);
    }

    /**
     * generate unique id for the given entity (assuming the entity isnt already
     * stored
     */
    private Integer generateNextEntityID(Entity entity) {

        // get the entity id if it already has one
        if (entity.getEntityID() != Entity.DEFAULT_ENTITY_ID && !(this.contains(entity.getEntityID()))) {
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
    public Entity despawn(Entity entity) {
        if (!(this.contains(entity))) {
            Logger.get().log(Logger.Level.WARNING, "Tried to remove an entity which wasnt in the world");
            return (null);
        }

        // remove from global list
        this.entities.remove(entity.getEntityID());

        // remove from type list
        ArrayList<Entity> type_list = this.getEntitiesByClass(entity);
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
    public void removeAll() {
        this.entities.clear();
        this.entitiesByClass.clear();
    }

    @Override
    public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {

        Collection<Entity> entities_collection = this.getEntities();
        int size = entities_collection.size();
        Entity[] entities = entities_collection.toArray(new Entity[size]);

        tasks.add(engine.new Callable<Taskable>() {

            @Override
            public WorldEntityStorage call() throws Exception {

                for (int i = 0; i < entities.length; i++) {
                    Entity entity = entities[i];
                    entity.update(engine.getTimer().getDt());
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
