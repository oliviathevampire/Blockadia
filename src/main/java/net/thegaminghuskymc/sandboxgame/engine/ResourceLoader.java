package net.thegaminghuskymc.sandboxgame.engine;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengles.GLES20.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengles.GLES20.glTexParameteri;

public class ResourceLoader {

	public static Texture loadTexture(String file) {
		try {
			Texture t = new Texture(TextureLoader.getTexture("png", new FileInputStream(new File("/assets/sandboxgame/textures/blocks/" + file))).getTextureID());
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			return t;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}