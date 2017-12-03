package net.thegaminghuskymc.sandboxgame.game.client.renderer;

public class EventPreRender extends EventRender {
	public EventPreRender(MainRenderer renderer) {
		super(renderer);
	}

	@Override
	public String getName() {
		return ("Pre Render");
	}
}
