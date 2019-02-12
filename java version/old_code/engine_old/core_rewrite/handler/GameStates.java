package team.hdt.blockadia.engine.core_rewrite.handler;

import team.hdt.blockadia.engine.core_rewrite.game.state.gamestate.MainMenuState;
import team.hdt.blockadia.engine.core_rewrite.game.state.gamestate.UnknownState;
import team.hdt.blockadia.engine.core_rewrite.game.state.gamestate.WorldState;
import team.hdt.blockadia.engine.core_rewrite.mod.GameRegistry;

public class GameStates {

	public static final int UNKNOWN = GameRegistry.registerGameState(UnknownState.class);
	public static final int WORLD = GameRegistry.registerGameState(WorldState.class);
	public static final int MAIN_MENU = GameRegistry.registerGameState(MainMenuState.class);
	/*public static final int SETTINGS = GameRegistry.registerGameState(MainMenuState.class);
	public static final int PAUSE = GameRegistry.registerGameState(MainMenuState.class);
	public static final int CREATE_WORLD = GameRegistry.registerGameState(MainMenuState.class);
	public static final int WORLD_LIST = GameRegistry.registerGameState(MainMenuState.class);
	public static final int SERVER_LIST = GameRegistry.registerGameState(MainMenuState.class);
	public static final int CREATIVE_INVENTORY = GameRegistry.registerGameState(MainMenuState.class);
	public static final int SURVIVAL_INVENTORY = GameRegistry.registerGameState(MainMenuState.class);
	public static final int CRAFTING_TABLE = GameRegistry.registerGameState(MainMenuState.class);
	public static final int FURNACE = GameRegistry.registerGameState(MainMenuState.class);
	public static final int CHEST = GameRegistry.registerGameState(MainMenuState.class);*/

}