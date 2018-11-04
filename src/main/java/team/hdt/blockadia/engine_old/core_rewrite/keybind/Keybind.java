package team.hdt.blockadia.engine.core_rewrite.keybind;

public class Keybind {

	private int keyCode;
	private boolean pressed;

	public Keybind(int keyCode) {
		this.keyCode = keyCode;
	}

	/**
	 * Called when this keybind is pressed.
	 */
	public void onPressed() {
	}

	/**
	 * Called when this keybind is released.
	 */
	public void onReleased() {
	}

	/**
	 * @return The id of the button that this is linked to
	 */
	public int getKeyCode() {
		return keyCode;
	}

	/**
	 * @return Whether or not this keybind is pressed. This is called after the single key is triggered
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * Sets this to pressed or not.
	 * 
	 * @param pressed
	 *            Whether or not this keybind is pressed
	 */
	protected void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}