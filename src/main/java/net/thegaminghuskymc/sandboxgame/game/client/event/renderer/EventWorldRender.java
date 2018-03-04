package net.thegaminghuskymc.sandboxgame.game.client.event.renderer;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.WorldRenderer;

@SuppressWarnings("rawtypes")
public abstract class EventWorldRender extends EventRender {

	private final WorldRenderer worldRenderer;

	public EventWorldRender(WorldRenderer worldRenderer) {
		super(worldRenderer.getMainRenderer());
		this.worldRenderer = worldRenderer;
	}

	public final WorldRenderer getWorldRenderer() {
		return (this.worldRenderer);
	}

}
