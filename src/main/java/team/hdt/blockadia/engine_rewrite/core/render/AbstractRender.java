package team.hdt.blockadia.engine_rewrite.core.render;


import team.hdt.blockadia.engine_rewrite.core.entitys.Entity;
import team.hdt.blockadia.engine_rewrite.core.entitys.EntityMod;
import team.hdt.blockadia.engine_rewrite.core.entitys.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRender {

    //ment to hold all living non player entitys in the client game
    public static List<Entity> entities = new ArrayList<Entity>();
    //ment to hold all living non player modified entitys in the client game
    public static List<EntityMod> entitiesMod = new ArrayList<EntityMod>();
    //ment to hold all living player entitys in the client game	(this is to future prof of multiplayer)
    public static List<EntityPlayer> players = new ArrayList<EntityPlayer>();
    public static boolean Modded = false;
    public static int entitycount = 0;
    public static int playercount = 0;

    public abstract void register();

    public abstract void render();

    public abstract void update();

    public abstract void cleanup();

    /**---------------------------------------
     *					DO NOT
     *				   OVERRIDE
     *				 	METHODS
     *					BELOW
     *----------------------------------------
     */
    /**
     * --------------------------------------
     * <p>
     * normal entity's
     * <p>
     * ---------------------------------------
     */
    //to add entity who joined the world
    public static void add_Mob(Entity mob) {
        entities.add(mob);
        entitycount++;
    }

    // to remove entity who left the world
    public static void remove_Mob(Entity mob) {
        entities.remove(mob);
        entitycount--;
    }

    /**
     * --------------------------------------
     * <p>
     * modified entity's
     * <p>
     * ---------------------------------------
     */
    //to add modified entity who joined the world
    public static void add_Mod_Mob(EntityMod mob) {
        entitiesMod.add(mob);
        entitycount++;
    }

    // to remove modified entity who left the world
    public static void remove_Mod_Mob(EntityMod mob) {
        entitiesMod.remove(mob);
        entitycount--;
    }

    /**
     * --------------------------------------
     * <p>
     * players
     * <p>
     * ---------------------------------------
     */
    //to add player who joined the world
    public static void player_Join(EntityPlayer player) {
        players.add(player);
        playercount++;
    }

    // to remove player who left the world
    public static void player_Leave(EntityPlayer player) {
        players.remove(player);
        playercount--;
    }

    /**
     * --------------------------------------
     * <p>
     * entity list and count update
     * <p>
     * ---------------------------------------
     */
    //to be called in main while loop
    public static void Update_Entitys_Count() {
        if (!(entitycount == 0)) {
            for (Entity entity : entities) {
                if (entity.isDead()) {
                    entities.remove(entity);
                    entitycount--;
                }
            }
            if (Modded == true) {
                for (EntityMod entity : entitiesMod) {
                    if (entity.isDead()) {
                        entitiesMod.remove(entity);
                        entitycount--;
                    }
                }
            }
            entityCount();
        } else {
            System.out.println("no entity's in the game");
        }
    }

    //used to update entity count after a mob die's
    private static void entityCount() {
        entitycount = 0;
        for (Entity entity : entities) {
            entitycount++;
        }
        if (Modded == true) {
            for (EntityMod entity : entitiesMod) {
                entitycount++;
            }
        }
    }

    /**
     * --------------------------------------
     * <p>
     * getters and setters
     * <p>
     * ---------------------------------------
     */
    public void is_Modded(boolean is_Modded) {
        this.Modded = is_Modded;
    }

    public int get_Player_Count() {
        return playercount;
    }

    public int get_Entity_Count() {
        return entitycount;
    }
}