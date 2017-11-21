package net.thegaminghuskymc.sandboxgame.engine;

import static org.lwjgl.opengl.GL11.*;

public class Texture implements Comparable<Texture> {
	
	private static int boundTexture = -1;
	
	private int id;

	public Texture(int id) {
		this.id = id;
	}
	
	public void bind() {
		if (boundTexture != id) {
			boundTexture = id;
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}

	public int getID() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Texture) {
			Texture t = (Texture) o;
			return t.id == id;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "Texture[id=" + id + "]";
	}

	@Override
	public int compareTo(Texture t) {
		return Integer.compare(id, t.id);
	}

}
