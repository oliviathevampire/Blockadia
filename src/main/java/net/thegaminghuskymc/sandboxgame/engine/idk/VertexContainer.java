package net.thegaminghuskymc.sandboxgame.engine.idk;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

public class VertexContainer {

	public float[] vertices;
	public FloatBuffer vertexBuffer;
	public int vertexId;

	public short[] indices;
	public ShortBuffer indexBuffer;
	public int indexId;

	public float[] colors;
	public FloatBuffer colorBuffer;
	public int colorId;

	public IntBuffer ib;
	public int type;

	public VertexContainer(float[] vertices) {
		this.vertices = vertices;
		this.indices = new short[] { 0, 1, 2, 0, 2, 3, 0, 1, 2, 0, 2, 3 };
		this.colors = new float[vertices.length];
		Arrays.fill(this.colors, 1);

		this.vertexBuffer = BufferUtils.createFloatBuffer(this.vertices.length);
		this.vertexBuffer.put(this.vertices);
		this.vertexBuffer.flip();

		this.indexBuffer = BufferUtils.createShortBuffer(this.indices.length);
		this.indexBuffer.put(this.indices);
		this.indexBuffer.flip();
		
		this.colorBuffer = BufferUtils.createFloatBuffer(this.colors.length);
		this.colorBuffer.put(this.colors);
		this.colorBuffer.flip();
		
		this.load();
	}

	private void load() {
		this.ib = BufferUtils.createIntBuffer(2);
		glGenBuffersARB(ib);

		this.vertexId = ib.get(0);
		this.colorId = ib.get(1);

		glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.vertexId);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.vertexBuffer, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);

		glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.indexId);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.indexBuffer, GL_STATIC_DRAW_ARB);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
	}
	
	public void render() {
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.vertexId);

		glVertexPointer(3, GL_FLOAT, 3 << 2, 0L);


//		glDisableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.colorId);
		glColorPointer(3, GL_FLOAT, 3 << 2, 0L);

//		glDisableClientState(GL_COLOR_ARRAY);
		
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER, this.indexId);
		
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}
}
