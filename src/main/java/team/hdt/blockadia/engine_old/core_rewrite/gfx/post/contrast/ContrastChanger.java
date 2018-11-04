package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.post.contrast;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.post.ImageRenderer;

public class ContrastChanger {

	private ImageRenderer renderer;
	private ContrastShader shader;

	public ContrastChanger(int width, int height, float contrast) {
		this.renderer = new ImageRenderer(width, width);
		this.shader = new ContrastShader();
		this.shader.start();
		this.shader.loadContrast(contrast);
		this.shader.stop();
	}

	public void render(int texture) {
		this.shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		this.renderer.renderQuad();
		this.shader.stop();
	}

	public void cleanUp() {
		this.renderer.cleanUp();
		this.shader.cleanUp();
	}

	public ImageRenderer getRenderer() {
		return renderer;
	}
}