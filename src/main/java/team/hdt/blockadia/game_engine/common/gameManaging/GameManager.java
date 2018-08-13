package team.hdt.blockadia.game_engine.common.gameManaging;

import team.hdt.blockadia.game_engine.client.rendering.EngineMaster;
import team.hdt.blockadia.game_engine.client.rendering.MasterRenderer;
import team.hdt.blockadia.game_engine.client.resourceManagement.ParticleAtlasCache;
import team.hdt.blockadia.game_engine.common.Main;
import team.hdt.blockadia.game_engine.common.util.time.Calendar;
import team.hdt.blockadia.game_engine.common.world.World;

public class GameManager {

	public static final GameStateManager gameState = new GameStateManager();
	private static int ticker = 0;
	private static float time = 0;
	private static boolean error = false;

	public static float TIME_SPEED = 1;

	public static void init() {
		ParticleAtlasCache.loadAll();
	}

	public static void update() {
		EngineMaster.preRenderUpdate();
		gameState.update();
		time += getGameSeconds();
		ticker ++;
	}
	
	public static void registerError(){
		error = true;
	}
	
	public static boolean checkError(){
		boolean e = error;
		error = false;
		return e;
	}
	
	public static int getTicker(){
		return ticker;
	}

	public static float getGameSeconds() {
		return Main.getDeltaSeconds() * TIME_SPEED;
	}
	
	public static float getDeltaHours() {
		return (Main.getDeltaSeconds() * TIME_SPEED) / Calendar.HOUR_LENGTH;
	}
	
	public static float getGameTime(){
		return time;
	}

	public static void render() {
		/*if (sessionManager.hasWorldReady()) {
			World world = GameManager.getWorld();
			MasterRenderer.render(world.getChunks(), GameManager.getSession().getSceneData().getDynamicBatch(),
					world.getWater(), true);
		}
		if(!MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_H)){
			MasterRenderer.renderGuis();
		}*/
		MasterRenderer.renderGuis();
		EngineMaster.update();
	}

	public static void cleanUp() {
		UserConfigs.saveConfigs();
		EngineMaster.close();
	}

	public static World getWorld() {
        return null;
	}

	public static GameState getGameState() {
		return gameState.getState();
	}

}
