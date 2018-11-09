package team.hdt.blockadia.engine.core_rewrite.gfx.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;
import team.hdt.blockadia.engine.core_rewrite.game.entity.Entity;
import team.hdt.blockadia.engine.core_rewrite.gfx.GlWrapper;
import team.hdt.blockadia.engine.core_rewrite.gfx.shader.EntityShader;
import team.hdt.blockadia.engine.core_rewrite.model.Model;
import team.hdt.blockadia.engine.core_rewrite.object.ICamera;
import team.hdt.blockadia.engine.core_rewrite.util.Loader;
import team.hdt.blockadia.engine.core_rewrite.util.Maths;

import java.util.List;
import java.util.Map;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Handles all the code necessary to render an entity to the screen.
 * 
 * @author Ocelot5836
 */
public class EntityRenderer {

	private static final float[] POSITIONS = new float[] { 0, 0, 0, 1, 1, 0, 1, 1 };

	private EntityShader shader;
	private Model quad;

	public EntityRenderer(EntityShader shader) {
		this.shader = shader;
		this.shader.start();
		this.shader.connectTextureUnits();
		this.shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		this.shader.stop();
		this.quad = Loader.loadToVAO(POSITIONS, 2);
	}

	public void render(Map<Identifier, List<Entity>> entities, ICamera camera, float partialTicks) {
		this.prepare();
		for (Identifier texture : entities.keySet()) {
			this.bindTexture(texture);
			shader.loadViewMatrix(camera);
			List<Entity> batch = entities.get(texture);
			for (Entity entity : batch) {
				Vector2f renderOffset = entity.getRenderOffset();
				shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.getRenderX(partialTicks) + (renderOffset != null ? renderOffset.x * entity.getScale() : 0), entity.getRenderY(partialTicks) + (renderOffset != null ? renderOffset.y * entity.getScale() : 0), entity.getRotation(), 32 * entity.getScale()));
				shader.loadTextureOffset(entity.getTextureOffset().x / (float) entity.getTextureWidth(), entity.getTextureOffset().y / (float) entity.getTextureWidth());
				shader.loadNumberOfRows(entity.getTextureWidth());
				entity.render(Blockadia.getInstance().getRenderer(), this, partialTicks);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
		}
		this.unbind();
	}

	private void bindTexture(Identifier texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		Blockadia.getInstance().getTextureManager().bind(texture);
	}

	private void prepare() {
		GlWrapper.disableDepth();
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
		GlWrapper.enableBlend();
		GlWrapper.enableDepth();
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