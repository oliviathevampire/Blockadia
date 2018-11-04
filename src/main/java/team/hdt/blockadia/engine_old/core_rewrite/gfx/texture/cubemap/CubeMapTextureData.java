package team.hdt.blockadia.engine.core_rewrite.gfx.texture.cubemap;

import java.nio.ByteBuffer;

public class CubeMapTextureData {

	private ByteBuffer data;
	private int width;
	private int height;

	public CubeMapTextureData(ByteBuffer data, int width, int height) {
		this.data = data;
		this.width = width;
		this.height = height;
	}

	public ByteBuffer getBuffer() {
		return data;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}