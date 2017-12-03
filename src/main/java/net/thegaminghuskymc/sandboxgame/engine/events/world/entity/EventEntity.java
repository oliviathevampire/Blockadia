package net.thegaminghuskymc.sandboxgame.engine.events.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;

public abstract class EventEntity extends Event
{
	private Entity _entity;
	
	public EventEntity(Entity entity)
	{
		this._entity = entity;
	}
	
	public Entity getEntity()
	{
		return (this._entity);
	}

}
