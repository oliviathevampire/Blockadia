package team.hdt.blockadia.engine.core_rewrite.game.state;

import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.object.ICamera;

import java.io.File;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A single state in the game. Can only be used once you call {@link GameStateManager#registerState(Class)}
 * 
 * @author Hypeirochus
 */
public class GameState {

	public GameState() {
	}

	/**
	 * Initializes the state.
	 */
	public void init() {
	}

	/**
	 * Updates this game state.
	 */
	public void update() {
	}

	/**
	 * Renders the state to the screen.
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
	public void render(MasterRenderer renderer, ICamera camera, double mouseX, double mouseY, float partialTicks) {
	}

	/**
	 * Called when a key is pressed.
	 * 
	 * @param keyCode
	 *            The code of the key pressed
	 */
	public void onKeyPressed(int keyCode) {
	}

	/**
	 * Called when a key is released.
	 * 
	 * @param keyCode
	 *            The code of the key released
	 */
	public void onKeyReleased(int keyCode) {
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
	public void onMousePressed(double mouseX, double mouseY, int mouseButton) {
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
	public void onMouseReleased(double mouseX, double mouseY, int mouseButton) {
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
	public void onMouseScrolled(double mouseX, double mouseY, double scroll) {
	}

	/**
	 * Called when the joystick is moved if one is present.
	 * 
	 * @param xDirection
	 *            The x axes of the joystick
	 * @param yDirection
	 *            The y axes of the joystick
	 */
	public void onJoystickMoved(double xDirection, double yDirection) {
	}

	/**
	 * Loads the state from file.
	 * 
	 * @param saveFolder
	 *            The folder that this save is handled in
	 */
	public void load(File saveFolder) {
	}

	/**
	 * Saves the state to file
	 * 
	 * @param saveFolder
	 *            The folder that this save is handled ino
	 */
	public void save(File saveFolder) {
	}

	/**
	 * Attempts to open a gui in the current state.
	 * 
	 * @param type
	 *            The type of gui that will be opened
	 */
	public void openGui(int type) {
	}
}