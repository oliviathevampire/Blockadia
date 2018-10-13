package team.hdt.blockadia.engine.core_rewrite.gfx.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;
import team.hdt.blockadia.engine.core_rewrite.gfx.GlWrapper;
import team.hdt.blockadia.engine.core_rewrite.gfx.shader.QuadShader;
import team.hdt.blockadia.engine.core_rewrite.model.Model;
import team.hdt.blockadia.engine.core_rewrite.object.Quad;
import team.hdt.blockadia.engine.core_rewrite.util.Loader;

import java.util.List;

public class QuadRenderer {

	private static final float[] POSITIONS = new float[] { 0, 1, 0, 0, 1, 1, 1, 0 };

	private QuadShader shader;
	private Model quad;

	public QuadRenderer(QuadShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(List<Quad> quads, float partialTicks) {
		this.bind();
		for (Quad quad : quads) {
			shader.loadColor(quad.getColor());
			shader.loadTransformationMatrix(quad.getRenderTransformation(Blockadia.getInstance().getRenderPartialTicks()));
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, this.quad.getVertexCount());
		}
		this.unbind();
	}

	private void bind() {
		GlWrapper.enableBlend();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}

	private void unbind() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
		GlWrapper.disableBlend();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}