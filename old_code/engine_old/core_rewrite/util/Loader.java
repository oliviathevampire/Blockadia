package team.hdt.blockadia.engine.core_rewrite.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;
import team.hdt.blockadia.engine.core_rewrite.gfx.texture.BasicTexture;
import team.hdt.blockadia.engine.core_rewrite.gfx.texture.ITexture;
import team.hdt.blockadia.engine.core_rewrite.gfx.texture.cubemap.CubeMapTextureData;
import team.hdt.blockadia.engine.core_rewrite.gfx.texture.cubemap.CubemapTexture;
import team.hdt.blockadia.engine.core_rewrite.model.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();
	private static List<Integer> textures = new ArrayList<>();

	public static void cleanUp() {
		for (Integer vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}

		for (Integer vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}

		for (Integer texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}

	public static ByteBuffer loadToByteBuffer(BufferedImage image) throws NullPointerException {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels;
		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++) {
		for (int x = 0; x < width; x++) {
				int color = pixels[x + y * width];
				buffer.put((byte) ((color >> 16) & 0xff));
				buffer.put((byte) ((color >> 8) & 0xff));
				buffer.put((byte) (color & 0xff));
				buffer.put((byte) ((color >> 24) & 0xff));
			}
		}
		buffer.flip();
		return buffer;
	}

		public static ITexture loadTexture(Identifier location) {
			try {
				return Loader.loadTexture(ImageIO.read(location.getInputStream()));
			} catch (Exception e) {
				Blockadia.logger().warn("Could not find image at \'" + location + "\'");
				return Loader.loadTexture(LoadingUtils.createMissingImage(256, 256));
			}
		}

		public static ITexture loadTexture(BufferedImage image) throws NullPointerException {
			int width = image.getWidth();
			int height = image.getHeight();

			ByteBuffer pixels = loadToByteBuffer(image);
			int textureID = GL11.glGenTextures();
			textures.add(textureID);

			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
			// GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

			return new BasicTexture(textureID, width, height);
		}

		public static ITexture loadCubemapTexture(Identifier folder) {
			Identifier right = new Identifier(folder, "right.png");
			Identifier left = new Identifier(folder, "left.png");
			Identifier top = new Identifier(folder, "top.png");
			Identifier bottom = new Identifier(folder, "bottom.png");
			Identifier back = new Identifier(folder, "back.png");
			Identifier front = new Identifier(folder, "front.png");
			return loadCubemapTexture(LoadingUtils.loadImage(right.toString(), right.getInputStream()), LoadingUtils.loadImage(left.toString(), left.getInputStream()), LoadingUtils.loadImage(top.toString(), top.getInputStream()), LoadingUtils.loadImage(bottom.toString(), bottom.getInputStream()), LoadingUtils.loadImage(back.toString(), back.getInputStream()), LoadingUtils.loadImage(front.toString(), front.getInputStream()));
		}

		public static ITexture loadCubemapTexture(BufferedImage right, BufferedImage left, BufferedImage top, BufferedImage bottom, BufferedImage back, BufferedImage front) throws NullPointerException {
			int textureID = GL11.glGenTextures();
			textures.add(textureID);

			CubeMapTextureData[] datas = new CubeMapTextureData[6];
			datas[0] = new CubeMapTextureData(loadToByteBuffer(right), right.getWidth(), right.getHeight());
			datas[1] = new CubeMapTextureData(loadToByteBuffer(left), left.getWidth(), left.getHeight());
			datas[2] = new CubeMapTextureData(loadToByteBuffer(top), top.getWidth(), top.getHeight());
			datas[3] = new CubeMapTextureData(loadToByteBuffer(bottom), bottom.getWidth(), bottom.getHeight());
			datas[4] = new CubeMapTextureData(loadToByteBuffer(back), back.getWidth(), back.getHeight());
			datas[5] = new CubeMapTextureData(loadToByteBuffer(front), front.getWidth(), front.getHeight());

			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);
			for (int i = 0; i < datas.length; i++) {
				CubeMapTextureData data = datas[i];
				GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
			}
			GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);

			return new CubemapTexture(textureID);
		}

		public static Model loadToVAO(float[] positions, int[] indices, float[] textureCoords, float[] normals) {
			int vaoID = GL30.glGenVertexArrays();
			vaos.add(vaoID);
			GL30.glBindVertexArray(vaoID);
			bindIndicesBuffer(vaoID, indices);
			storeDataInAttributeList(0, 3, positions);
			storeDataInAttributeList(1, 2, textureCoords);
			storeDataInAttributeList(2, 3, normals);
			GL30.glBindVertexArray(0);
			return new Model(vaoID, indices.length);
		}

		public static Model loadToVAO(float[] positions, int[] indices, int dimensions) {
			int vaoID = GL30.glGenVertexArrays();
			vaos.add(vaoID);
			GL30.glBindVertexArray(vaoID);
			bindIndicesBuffer(vaoID, indices);
			storeDataInAttributeList(0, dimensions, positions);
			GL30.glBindVertexArray(0);
			return new Model(vaoID, indices.length);
		}

		public static Model loadToVAO(float[] positions, int dimensions) {
			int vaoID = GL30.glGenVertexArrays();
			vaos.add(vaoID);
			GL30.glBindVertexArray(vaoID);
			storeDataInAttributeList(0, dimensions, positions);
			GL30.glBindVertexArray(0);
			return new Model(vaoID, positions.length / dimensions);
		}

		public static int createEmptyVBO(int floatCount) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static void storeInstancedDataInAttributeList(int vao, int vbo, int attributeNumber, int dataSize, int instancedDataLength, int offset) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attributeNumber, dataSize, GL11.GL_FLOAT, false, instancedDataLength * 4, offset * 4);
		GL33.glVertexAttribDivisor(attributeNumber, 1);
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public static void updateVboData(int vbo, float[] data, FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_DYNAMIC_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private static void bindIndicesBuffer(int vao, int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = Buffers.storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL30.glBindVertexArray(0);
	}

	public static void storeDataInAttributeList(int attributeNumber, int dataSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = Buffers.storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
}