package team.hdt.blockadia.old_engine_code_1.core_rewrite.game.state;

import com.google.common.collect.Maps;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.handler.GameStates;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.mod.GameRegistry;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.object.ICamera;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Handles all the states in the game.
 * 
 * @author Hypeirochus
 */
public class GameStateManager {

	private static final Map<Integer, Class<? extends GameState>> REGISTRY = Maps.<Integer, Class<? extends GameState>>newHashMap();

	private static int nextId;
	private static GameState state;

	/**
	 * Initializes the game state manager.
	 */
	public static void init() {
		// GameStateManager.manager.addGameState(new GameState("STARTUP"));
		// GameStateManager.manager.addGameState(new GameState("MAINMENU"));
		// GameStateManager.manager.addGameState(new GameState("SETTINGS"));
		// GameStateManager.manager.addGameState(new GameState("LOADING"));
		// GameStateManager.manager.addGameState(new GameState("WORLDMENU"));
		// GameStateManager.manager.addGameState(new GameState("WORLD"));
		// GameStateManager.manager.addGameState(new GameState("INGAMESETTINGS"));
		GameStateManager.setCurrentState(GameStates.WORLD);
	}

	/**
	 * Updates the selected state.
	 */
	public static void update() {
		state.update();
	}

	/**
	 * Renders the selected state to the screen.
	 * 
	 * @param renderer
	 *            The main game renderer
	 * @param camera
	 *            The camera instance
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @param partialTicks
	 *            The percentage between last update and this update
	 */
	public static void render(MasterRenderer renderer, ICamera camera, double mouseX, double mouseY, float partialTicks) {
		state.render(renderer, camera, mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when a key is pressed.
	 * 
	 * @param keyCode
	 *            The code of the key pressed
	 */
	public static void onKeyPressed(int keyCode) {
		state.onKeyPressed(keyCode);
	}

	/**
	 * Called when a key is released.
	 * 
	 * @param keyCode
	 *            The code of the key released
	 */
	public static void onKeyReleased(int keyCode) {
		state.onKeyReleased(keyCode);
	}

	/**
	 * Called when a mouse button is pressed.
	 * 
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @param mouseButton
	 *            The button pressed
	 */
	public static void onMousePressed(double mouseX, double mouseY, int mouseButton) {
		state.onMousePressed(mouseX, mouseY, mouseButton);
	}

	/**
	 * Called when a mouse button is released.
	 * 
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @param mouseButton
	 *            The button released
	 */
	public static void onMouseReleased(double mouseX, double mouseY, int mouseButton) {
		state.onMouseReleased(mouseX, mouseY, mouseButton);
	}

	/**
	 * Called when the mouse is scrolled.
	 * 
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @param scroll
	 *            The amount scrolled
	 */
	public static void onMouseScrolled(double mouseX, double mouseY, double scroll) {
		state.onMouseScrolled(mouseX, mouseY, scroll);
	}

	/**
	 * Called when the joystick is moved if one is present.
	 * 
	 * @param xDirection
	 *            The x axes of the joystick
	 * @param yDirection
	 *            The y axes of the joystick
	 */
	public static void onJoystickMoved(double xDirection, double yDirection) {
		state.onJoystickMoved(xDirection, yDirection);
	}

	/**
	 * Saves current game.
	 */
	public static void save(File saveFolder) {
		state.save(saveFolder);
	}

	/**
	 * Loads up the game.
	 */
	public static void load(File saveFolder) {
		state.load(saveFolder);
	}

	/**
	 * Attempts to open a gui in the current state.
	 * 
	 * @param type
	 *            The type of gui that will be opened
	 */
	public static void openGui(int type) {
		state.openGui(type);
	}

	/**
	 * Registers a state to the game state manager.
	 * 
	 * @param gameState
	 *            The state to register
	 * @deprecated This should not be used by modders. Use {@link GameRegistry#registerGameState(Class)} instead.
	 */
	public static int registerState(Class<? extends GameState> gameState) {
		int id = nextId++;
		if (REGISTRY.containsValue(gameState)) {
			throw new RuntimeException(gameState.getName() + " was already registered!");
		}
		if (!REGISTRY.containsKey(id)) {
			REGISTRY.put(id, gameState);
		} else {
			throw new RuntimeException("Attempted to register a game state over another. OLD: " + GameStateManager.REGISTRY.get(id).getName() + ", NEW: " + gameState.getName());
		}
		return id;
	}

	/**
	 * Sets the current state to the one supplied.
	 * 
	 * @param state
	 *            The new state of the game state manager
	 */
	public static void setCurrentState(int state) {
		GameState newState = createNewState(state);
		if (newState != null) {
			newState.init();
			GameStateManager.state = newState;
		}
	}

	/**
	 * Creates a new state based on the id.
	 * 
	 * @param gameState
	 *            The state id
	 * @return The state created or null if the game state doesn't exist
	 */
	@Nullable
	private static GameState createNewState(int gameState) {
		try {
			Class<? extends GameState> clazz = REGISTRY.get(gameState);
			if (clazz != null) {
				return clazz.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return An entry set containing all the states registered
	 */
	public static Set<Entry<Integer, Class<? extends GameState>>> getGameStates() {
		return REGISTRY.entrySet();
	}

	/**
	 * @return The current state
	 */
	@Nonnull
	public static GameState getCurrentState() {
		return state;
	}
}
