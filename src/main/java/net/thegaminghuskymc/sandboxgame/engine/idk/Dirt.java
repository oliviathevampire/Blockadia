package net.thegaminghuskymc.sandboxgame.engine.idk;

/**
 * A Dirt Colorist
 * 
 * @author Lane Aasen <laneaasen@gmail.com>
 * 
 */

import static org.lwjgl.opengl.GL11.glColor3f;

public class Dirt extends Colorist {
	public Dirt(float range, float offset) {
		super(range, offset);
	}
	
	/**
	 * Selects a color based on the value
	 * 
	 * @param value
	 */
	public void color(float value) {
		value = this.value(value);
		glColor3f(0.5f * value, 0.25f * value, 0.0f * value);
	}
}