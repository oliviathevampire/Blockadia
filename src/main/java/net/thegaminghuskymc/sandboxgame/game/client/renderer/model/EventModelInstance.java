package net.thegaminghuskymc.sandboxgame.game.client.renderer.model;

import net.thegaminghuskymc.sandboxgame.engine.events.Event;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

public class EventModelInstance extends Event {
	private final ModelInstance modelInstance;

	public EventModelInstance(ModelInstance modelInstance) {
		this.modelInstance = modelInstance;
	}

	public final ModelInstance getModelInstance() {
		return (this.modelInstance);
	}
}
