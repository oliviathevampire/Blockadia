package team.hdt.blockadia.game_engine.common.gameManaging;

import team.hdt.blockadia.test.Main;

public class Ticker {

	private static int nextId = 1;
	
	private final int id;
	private final int periodInFrames;
	
	public Ticker(float period){
		periodInFrames = (int) (period * Main.FPS_CAP);
		this.id = nextId++ % periodInFrames;
	}
	
	public boolean isActive(){
		return GameManager.getTicker() % periodInFrames == id;
	}

}
