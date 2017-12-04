package net.thegaminghuskymc.sandboxgame.engine.events;


import net.thegaminghuskymc.sandboxgame.engine.events.world.*;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntityDespawn;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntityJump;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntityPlaySound;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntitySpawn;
import net.thegaminghuskymc.sandboxgame.engine.managers.EventManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;

public class Events implements IModResource {

    @Override
    public void load(Mod mod, ResourceManager manager) {
        // default events
        EventManager eventManager = manager.getEventManager();

        // entities event
        eventManager.registerEvent(EventEntityJump.class);
        eventManager.registerEvent(EventEntitySpawn.class);
        eventManager.registerEvent(EventEntityDespawn.class);
        eventManager.registerEvent(EventEntityPlaySound.class);

        // world event
        eventManager.registerEvent(EventTerrainSpawn.class);
        eventManager.registerEvent(EventTerrainDespawn.class);
        eventManager.registerEvent(EventTerrainSetBlock.class);
        eventManager.registerEvent(EventTerrainDurabilityChanged.class);
        eventManager.registerEvent(EventTerrainBlocklightUpdate.class);
        eventManager.registerEvent(EventTerrainSunlightUpdate.class);

        // engine events
        eventManager.registerEvent(EventGetTasks.class);
        eventManager.registerEvent(EventPreLoop.class);
        eventManager.registerEvent(EventOnLoop.class);
        eventManager.registerEvent(EventPostLoop.class);
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }
}
