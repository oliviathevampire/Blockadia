package team.hdt.blockadia.engine.core_rewrite.gfx.post.bloom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import team.hdt.blockadia.engine.core_rewrite.gfx.post.ImageRenderer;

public class CombineFilter {

	private ImageRenderer renderer;
	private CombineShader shader;

	public CombineFilter(int width, int height) {
		shader = new CombineShader();
		renderer = new ImageRenderer(width, height);
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
	}

	public void render(int colorTexture, int highlightTexture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture);
		renderer.renderQuad();
		shader.stop();
	}

	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}

	public ImageRenderer getRenderer() {
		return renderer;
	}
}