package team.hdt.sandboxgame.game_engine.client.particles;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL31;
import team.hdt.sandboxgame.game_engine.client.resourceManagement.ParticleAtlasCache;
import team.hdt.sandboxgame.game_engine.common.CameraInterface;
import team.hdt.sandboxgame.game_engine.common.Loader;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.world.misc.EnvironmentVariables;
import team.hdt.sandboxgame.game_engine.util.toolbox.Colour;
import team.hdt.sandboxgame.game_engine.util.toolbox.OpenglUtils;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

public class ParticleRenderer {

	private static final int MAX_PARTICLES = 10000;

	private static final float[] VERTICES = { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f };
	private static final int INSTANCE_DATA_SIZE = 21;
	private FloatBuffer buffer;

	private int vao;
	private int vbo;
	private ParticleShader shader;
	private ParticleColourShader colourShader;
	
	private Vectors3f lighting = new Vectors3f();
	private Vectors3f glowLight = new Vectors3f(1,1,1);
	
	private int pointer = 0;

	protected ParticleRenderer(Matrix4fs projectionMatrix) {
		vao = Loader.createInterleavedVAO(VERTICES, 2);
		vbo = Loader.createInterleavedInstancedVbo(vao, MAX_PARTICLES, 1, 4, 4, 4, 4, 4, 1);
		buffer = BufferUtils.createFloatBuffer(MAX_PARTICLES * INSTANCE_DATA_SIZE);
		initShaders(projectionMatrix);
	}

	protected void render(Map<ParticleTexture, List<Particle>> particles, List<Particle> colourParticles,
			List<Particle> additiveColourParticles, CameraInterface camera) {
		Matrix4fs viewMatrix = camera.getViewMatrix();
		prepare();
		shader.start();
		for (ParticleTexture texture : particles.keySet()) {
			renderTexturedParticles(texture, particles, viewMatrix);
		}
		shader.stop();
		colourShader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ParticleAtlasCache.TRIANGLE.getID());
		renderColourParticles(colourParticles, viewMatrix, false);
		renderColourParticles(additiveColourParticles, viewMatrix, true);
		colourShader.stop();
		finishRendering();
	}

	protected void cleanUp() {
		shader.cleanUp();
	}
	
	private void calculateLightingFactor(){
		Vectors3f lightDir = EnvironmentVariables.getVariables().getLightDirection();
		float brightness = Math.max(0, -lightDir.y);
		Vectors3f diffuse = new Vectors3f(EnvironmentVariables.getVariables().getLightColour().getVector());
		diffuse.scale(brightness * EnvironmentVariables.getVariables().getDiffuseWeighting());
		Vectors3f ambient = new Vectors3f(EnvironmentVariables.getVariables().getLightColour().getVector());
		ambient.scale(EnvironmentVariables.getVariables().getAmbientWeighting());
		Vectors3f.add(diffuse, ambient, lighting);
	}

	private void renderTexturedParticles(ParticleTexture texture, Map<ParticleTexture, List<Particle>> particles,
			Matrix4fs viewMatrix) {
		List<Particle> particleList = particles.get(texture);
		bindTexture(texture);
		float[] vboData = new float[particleList.size() * INSTANCE_DATA_SIZE];
		pointer = 0;
		for (Particle particle : particleList) {
			updateModelViewMatrix(particle, viewMatrix, vboData);
			updateTexCoordInfo(particle, vboData);
		}
		Loader.refillVboWithData(vbo, buffer, vboData);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, 4, particleList.size());
	}

	private void renderColourParticles(List<Particle> colourParticles, Matrix4fs viewMatrix, boolean additive) {
		if (additive) {
			colourShader.lighting.loadVec3(glowLight);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		} else {
			colourShader.lighting.loadVec3(lighting);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		float[] vboData = new float[colourParticles.size() * INSTANCE_DATA_SIZE];
		pointer = 0;
		for (Particle particle : colourParticles) {
			updateModelViewMatrix(particle, viewMatrix, vboData);
			updateColourInfo(particle, vboData);
			vboData[pointer++] = particle.getTransparency();
		}
		Loader.refillVboWithData(vbo, buffer, vboData);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, 4, colourParticles.size());
	}

	private void updateTexCoordInfo(Particle particle, float[] data) {
		data[pointer++] = particle.getTexOffset1().x;
		data[pointer++] = particle.getTexOffset1().y;
		data[pointer++] = particle.getTexOffset2().x;
		data[pointer++] = particle.getTexOffset2().y;
		data[pointer++] = particle.getBlend();
	}

	private void updateColourInfo(Particle particle, float[] data) {
		Colour colour = particle.getColour();
		data[pointer++] = colour.getR();
		data[pointer++] = colour.getG();
		data[pointer++] = colour.getB();
		data[pointer++] = 1;
	}

	private void bindTexture(ParticleTexture texture) {
		bindTexture(texture.getTextureID(), texture.isAdditive(), texture.glows());
		shader.numberOfRows.loadFloat(texture.getNumberOfRows());
	}

	private void initShaders(Matrix4fs projectionMatrix) {
		shader = new ParticleShader();
		shader.start();
		shader.projectionMatrix.loadMatrix(projectionMatrix);
		shader.stop();
		colourShader = new ParticleColourShader();
		colourShader.start();
		colourShader.projectionMatrix.loadMatrix(projectionMatrix);
		colourShader.stop();
	}

	private void bindTexture(int textureId, boolean additive, boolean glows) {
		if (additive) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			shader.lighting.loadVec3(glowLight);
		} else {
			if(glows){
				shader.lighting.loadVec3(glowLight);
			}else{
				shader.lighting.loadVec3(lighting);
			}
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
	}

	private void updateModelViewMatrix(Particle p, Matrix4fs viewMatrix, float[] vboData) {
		Matrix4fs modelMatrix = new Matrix4fs();
		Matrix4fs.translate(p.getPosition(), modelMatrix, modelMatrix);
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
		Matrix4fs modelViewMatrix = Matrix4fs.mul(viewMatrix, modelMatrix, null);
		Matrix4fs.rotate((float) Math.toRadians(p.getRotation()), new Vectors3f(0, 0, 1), modelViewMatrix,
				modelViewMatrix);
		Matrix4fs.rotate((float) Math.toRadians(p.getRotX()), new Vectors3f(0, 1, 0), modelViewMatrix, modelViewMatrix);
		float scale = p.getScale();
		Matrix4fs.scale(new Vectors3f(scale, scale, scale), modelViewMatrix, modelViewMatrix);
		storeMatrixData(modelViewMatrix, vboData);
	}

	private void storeMatrixData(Matrix4fs matrix, float[] data) {
		data[pointer++] = matrix.m00;
		data[pointer++] = matrix.m01;
		data[pointer++] = matrix.m02;
		data[pointer++] = matrix.m03;
		data[pointer++] = matrix.m10;
		data[pointer++] = matrix.m11;
		data[pointer++] = matrix.m12;
		data[pointer++] = matrix.m13;
		data[pointer++] = matrix.m20;
		data[pointer++] = matrix.m21;
		data[pointer++] = matrix.m22;
		data[pointer++] = matrix.m23;
		data[pointer++] = matrix.m30;
		data[pointer++] = matrix.m31;
		data[pointer++] = matrix.m32;
		data[pointer++] = matrix.m33;
	}

	private void prepare() {
		OpenglUtils.bindVAO(vao, 0, 1, 2, 3, 4, 5, 6);
		OpenglUtils.cullBackFaces(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
		calculateLightingFactor();
	}

	private void finishRendering() {
		GL11.glDepthMask(true);
		OpenglUtils.cullBackFaces(true);
		GL11.glDisable(GL11.GL_BLEND);
		OpenglUtils.unbindVAO(0, 1, 2, 3, 4, 5, 6);
	}

}
