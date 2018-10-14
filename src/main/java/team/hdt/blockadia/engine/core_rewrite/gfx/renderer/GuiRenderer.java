package team.hdt.blockadia.engine.core_rewrite.gfx.renderer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import team.hdt.blockadia.engine.core_rewrite.Display;
import team.hdt.blockadia.engine.core_rewrite.gfx.GlWrapper;
import team.hdt.blockadia.engine.core_rewrite.gfx.gui.Gui;
import team.hdt.blockadia.engine.core_rewrite.gfx.gui.GuiTexture;
import team.hdt.blockadia.engine.core_rewrite.gfx.shader.GuiShader;
import team.hdt.blockadia.engine.core_rewrite.model.Model;
import team.hdt.blockadia.engine.core_rewrite.util.Loader;

import java.util.List;

public class GuiRenderer {

	public static float scale = 3f;
	private static Matrix4f projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);

	private static final float[] POSITIONS = new float[] { 0, 1, 0, 0, 1, 1, 1, 0 };

	private GuiShader shader;
	private Model quad;

	public GuiRenderer(GuiShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(List<Gui> guis, double mouseX, double mouseY, float partialTicks) {
		this.bind();
		for (Gui gui : guis) {
			for (GuiTexture texture : gui.getTextures()) {
				shader.loadTransformationMatrix(texture.getTransformation());
				shader.loadTexture(texture);
				if (texture.getType() == GuiTexture.TEXTURE) {
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture().getId());
				}
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, this.quad.getVertexCount());
			}
			gui.getTextures().clear();
		}
		this.unbind();
	}

	private void bind() {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GlWrapper.disableDepth();
		GlWrapper.enableBlend();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void unbind() {
		GlWrapper.disableBlend();
		GlWrapper.enableDepth();
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

	public void setScale(float scale) {
		GuiRenderer.scale = scale;
		GuiRenderer.projectionMatrix = new Matrix4f().ortho(0, Display.getWidth() / scale, Display.getHeight() / scale, 0, 0.3f, 1000f);
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}