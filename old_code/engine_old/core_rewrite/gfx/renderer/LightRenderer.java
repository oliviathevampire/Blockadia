package team.hdt.blockadia.engine.core_rewrite.gfx.renderer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import team.hdt.blockadia.engine.core_rewrite.gfx.GlWrapper;
import team.hdt.blockadia.engine.core_rewrite.gfx.light.Light;
import team.hdt.blockadia.engine.core_rewrite.gfx.shader.LightShader;
import team.hdt.blockadia.engine.core_rewrite.model.Model;
import team.hdt.blockadia.engine.core_rewrite.object.ICamera;
import team.hdt.blockadia.engine.core_rewrite.util.Loader;

import java.util.List;

public class LightRenderer {

	private static final float[] POSITIONS = new float[] { -1, 1, -1, -1, 1, 1, 1, -1 };

	private LightShader shader;
	private Model quad;

	public LightRenderer(LightShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(List<Light> lights, ICamera camera, float partialTicks) {
		this.bind();
		shader.loadViewMatrix(camera);
		for (Light light : lights) {
			shader.loadLight(light);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		this.unbind();
	}

	private void bind() {
		GlWrapper.enableBlend();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
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
	
	public void updateProjectionMatrix(Matrix4f matrix) {
		this.shader.start();
		this.shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		this.shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
}