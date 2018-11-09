package team.hdt.blockadia.engine.core_rewrite.keybind;

import com.google.common.collect.Maps;
import team.hdt.blockadia.engine.core_rewrite.mod.GameRegistry;

import java.util.Map;

public class KeybindManager {

	private static final Map<Integer, Keybind> REGISTRY = Maps.<Integer, Keybind>newHashMap();

	private KeybindManager() {
	}

	/**
	 * Called when a key is pressed.
	 * 
	 * @param keyCode
	 *            The code of the key pressed
	 */
	public static void onKeyPressed(int keyCode) {
		Keybind keyBind = REGISTRY.get(keyCode);
		if (keyBind != null) {
			keyBind.setPressed(true);
			keyBind.onPressed();
		}
	}

	/**
	 * Called when a key is released.
	 * 
	 * @param keyCode
	 *            The code of the key released
	 */
	public static void onKeyReleased(int keyCode) {
		Keybind keyBind = REGISTRY.get(keyCode);
		if (keyBind != null) {
			keyBind.setPressed(false);
			keyBind.onReleased();
		}
	}

	/**
	 * Registers a keybind.
	 * 
	 * @param key
	 *            The key that this will be bound to
	 * @return The keybind created
	 * 
	 * @deprecated This should not be used by modders. Use {@link GameRegistry#registerKeybind(int)} instead.
	 */
	public static Keybind registerKeyBind(int key) {
		if (!REGISTRY.containsKey(key)) {
			REGISTRY.put(key, new Keybind(key));
			return REGISTRY.get(key);
		} else {
			throw new RuntimeException("Attempted to register a keybind over another. OLD: " + key + ", NEW: " + key);
		}
	}
}