package net.thegaminghuskymc.sandboxgame.game.client.renderer;

import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.WorldRenderer;

public abstract class EventWorldRender extends EventRender {

	private final WorldRenderer<World> worldRenderer;

	public EventWorldRender(WorldRenderer<World> worldRenderer) {
		super(worldRenderer.getMainRenderer());
		this.worldRenderer = worldRenderer;
	}

	public final WorldRenderer<World> getWorldRenderer() {
		return (this.worldRenderer);
	}

}
