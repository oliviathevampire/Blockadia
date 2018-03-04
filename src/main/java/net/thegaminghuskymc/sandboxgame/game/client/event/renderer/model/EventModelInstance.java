package net.thegaminghuskymc.sandboxgame.game.client.event.renderer.model;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

public abstract class EventModelInstance extends Event {
	private final ModelInstance modelInstance;

	public EventModelInstance(ModelInstance modelInstance) {
		super();
		this.modelInstance = modelInstance;
	}

	public final ModelInstance getModelInstance() {
		return (this.modelInstance);
	}
}
