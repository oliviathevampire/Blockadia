package team.hdt.blockadia.engine.core_rewrite.gfx.post.blur;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import team.hdt.blockadia.engine.core_rewrite.gfx.post.ImageRenderer;

public class HorizontalBlur {

	private ImageRenderer renderer;
	private HorizontalBlurShader shader;

	public HorizontalBlur(int targetFboWidth, int targetFboHeight) {
		shader = new HorizontalBlurShader();
		renderer = new ImageRenderer(targetFboWidth, targetFboHeight);
		shader.start();
		shader.loadTargetWidth(targetFboWidth);
		shader.stop();
	}

	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
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