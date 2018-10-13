package team.hdt.blockadia.engine.core_rewrite.gfx;

import org.lwjgl.opengl.GL11;

public class GlWrapper {

	/** Util */

	public static void pushMatrix() {
		GL11.glPushMatrix();
	}

	public static void popMatrix() {
		GL11.glPopMatrix();
	}

	/** Triggers */

	public static void enableDepth() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void disableDepth() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public static void enableAlpha() {
		GL11.glEnable(GL11.GL_ALPHA);
	}

	public static void disableAlpha() {
		GL11.glDisable(GL11.GL_ALPHA);
	}

	public static void enableBlend() {
		GL11.glEnable(GL11.GL_BLEND);
	}

	public static void disableBlend() {
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void enableTexture2D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void disableTexture2D() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public static void cullFace(int face) {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(face);
	}
	
	public static void disableCull() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
}