package net.thegaminghuskymc.sandboxgame.game.client.defaultmod;

import net.thegaminghuskymc.sandboxgame.engine.events.Listener;
import net.thegaminghuskymc.sandboxgame.engine.events.world.entity.EventEntityPlaySound;
import net.thegaminghuskymc.sandboxgame.engine.managers.EventManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.openal.ALH;
import net.thegaminghuskymc.sandboxgame.game.client.openal.ALSound;
import net.thegaminghuskymc.sandboxgame.game.client.resources.ResourceManagerClient;

public class ClientEvents implements IModResource {

    @Override
    public void load(Mod mod, ResourceManager manager) {
        // default events
        EventManager eventManager = manager.getEventManager();

        // TODO keep this here?
        eventManager.addListener(new Listener<EventEntityPlaySound>() {

            @Override
            public void pre(EventEntityPlaySound event) {
            }

            @Override
            public void post(EventEntityPlaySound event) {
                WorldEntity e = event.getEntity();
                float x = e.getPositionX();
                float y = e.getPositionY();
                float z = e.getPositionZ();
                float vx = e.getPositionVelocityX();
                float vy = e.getPositionVelocityY();
                float vz = e.getPositionVelocityZ();
                Vector3f pos = new Vector3f(x, y, z);
                Vector3f vel = new Vector3f(vx, vy, vz);
                ALSound sound = ALH.alhLoadSound(event.getSound());
                ((ResourceManagerClient) manager).getSoundManager().playSoundAt(sound, pos, vel);
            }
        });
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }
}
