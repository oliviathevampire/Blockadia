package net.thegaminghuskymc.sandboxgame.game.client.tests;

import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;

public class LoadingUnloadingTest {

	public static void main(String[] args) {

		/* 1 */
		// initialize engine
		GameEngineClient engine = new GameEngineClient();
		engine.initialize();

		// load resources (mods)
		engine.load();

		try {
			engine.loop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		engine.deinitialize();
	}
}