package team.hdt.blockadia.game_engine.common.gameManaging;

import team.hdt.blockadia.game_engine.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine.common.Camera;

public enum GameState {
	

	GRABBING(() -> {
		GuiMaster.enableMouseInteraction(false);
		System.out.println("Grabbing mode");
	}),
	PLACING(() -> {
		System.out.println("Placing mode");
	}),
	IN_GUI(() -> {
		Camera.getCamera().enable(false);
		System.out.println("Gui mode");
	}),
	BIOME_PICKING(() -> {
		System.out.println("Biome mode");
	}),
	CAMERA(() -> {
		GuiMaster.enableMouseInteraction(false);
		System.out.println("Camera mode");
	}),
	GAME_MENU(() -> {
		Camera.getCamera().enable(false);
		System.out.println("Game Menu mode");
	}),
	SPLASH_SCREEN(() -> {
		Camera.getCamera().enable(false);
		GuiMaster.enableMouseInteraction(false);
		System.out.println("Splash Screen mode");
	}),
	INTENSE_GUI(() -> {
		Camera.getCamera().enable(false);
		System.out.println("Intense GUI");
	}),
	CONTROL(() -> {
		System.out.println("Control Mode");
	});
	
	private StateChangeAction state;
	
	private GameState(StateChangeAction state){
		this.state = state;
	}
	
	public void init(){
		state.init();
	}

}
