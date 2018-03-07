package net.thegaminghuskymc.sandboxgame.engine.events.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;

public abstract class EventEntity extends Event {
    private final WorldEntity entity;

    public EventEntity(WorldEntity entity) {
        super();
        this.entity = entity;
    }

    public final WorldEntity getEntity() {
        return (this.entity);
    }

}
