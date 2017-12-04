package net.thegaminghuskymc.sandboxgame.game.client.defaultmod;

import net.thegaminghuskymc.sandboxgame.engine.managers.EventManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.*;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.EventModelInstanceAdded;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.EventModelInstanceRemoved;

public class ClientEvents implements IModResource {

    @Override
    public void load(Mod mod, ResourceManager manager) {
        // default events
        EventManager eventManager = manager.getEventManager();

        // rendering events
        eventManager.registerEvent(EventPostRender.class);
        eventManager.registerEvent(EventPreRender.class);
        eventManager.registerEvent(EventPostWorldRender.class);
        eventManager.registerEvent(EventPreWorldRender.class);
        eventManager.registerEvent(EventPostRendererInitialisation.class);
        eventManager.registerEvent(EventModelInstanceAdded.class);
        eventManager.registerEvent(EventModelInstanceRemoved.class);

    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }
}
