package team.hdt.blockadia.engine.core_rewrite.gfx.post;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import team.hdt.blockadia.engine.core_rewrite.Display;
import team.hdt.blockadia.engine.core_rewrite.gfx.GlWrapper;
import team.hdt.blockadia.engine.core_rewrite.gfx.post.bloom.CombineFilter;
import team.hdt.blockadia.engine.core_rewrite.gfx.post.blur.Blur;
import team.hdt.blockadia.engine.core_rewrite.gfx.post.light.LightApplier;
import team.hdt.blockadia.engine.core_rewrite.model.Model;
import team.hdt.blockadia.engine.core_rewrite.util.Loader;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static Model quad;

	private static Blur blur;
	private static LightApplier lightApplier;
	private static CombineFilter combineFilter;

	public static void init() {
		quad = Loader.loadToVAO(POSITIONS, 2);

		blur = new Blur(Display.getWidth() / 4, Display.getHeight() / 4);
		combineFilter = new CombineFilter(Display.getWidth(), Display.getHeight());
		lightApplier = new LightApplier(Display.getWidth(), Display.getHeight());
	}

	public static void doPostProcessing(int colorTexture, int brightTexture, int lightColorTexture) {
		start();
		blur.render(brightTexture);
		combineFilter.render(colorTexture, blur.getRenderer().getOutputTexture());
		lightApplier.render(combineFilter.getRenderer().getOutputTexture(), lightColorTexture);
		end();
	}

	public static void cleanUp() {
		blur.cleanUp();
		combineFilter.cleanUp();
		lightApplier.cleanUp();
	}

	private static void start() {
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GlWrapper.disableDepth();
	}

	private static void end() {
		GlWrapper.enableDepth();
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}