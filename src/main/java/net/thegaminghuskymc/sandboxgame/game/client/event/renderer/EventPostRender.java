package net.thegaminghuskymc.sandboxgame.game.client.event.renderer;


import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;

public class EventPostRender extends EventRender {
	public EventPostRender(MainRenderer renderer) {
		super(renderer);
	}

	@Override
	public String getName() {
		return ("Post World Render");
	}

	@Override
	protected void process() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void unprocess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		
	}
}
