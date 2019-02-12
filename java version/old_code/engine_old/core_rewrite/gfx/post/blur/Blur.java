package team.hdt.blockadia.engine.core_rewrite.gfx.post.blur;

import team.hdt.blockadia.engine.core_rewrite.gfx.post.ImageRenderer;

public class Blur {

	private HorizontalBlur horizontal;
	private VerticalBlur vertical;

	public Blur(int width, int height) {
		horizontal = new HorizontalBlur(width, height);
		vertical = new VerticalBlur(width, height);
	}

	public void render(int texture) {
		horizontal.render(texture);
		vertical.render(horizontal.getRenderer().getOutputTexture());
	}

	public void cleanUp() {
		horizontal.cleanUp();
		vertical.cleanUp();
	}
	
	public HorizontalBlur getHorizontal() {
		return horizontal;
	}
	
	public VerticalBlur getVertical() {
		return vertical;
	}

	public ImageRenderer getRenderer() {
		return vertical.getRenderer();
	}
}