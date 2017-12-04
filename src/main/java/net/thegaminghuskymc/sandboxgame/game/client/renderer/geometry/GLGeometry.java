package net.thegaminghuskymc.sandboxgame.game.client.renderer.geometry;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

/**
 * generate float buffer for opengl
 */
public class GLGeometry {

    public static ByteBuffer generateSphere(int detail, float size) {
        return (toByteBuffer(Sphere.make(detail, size)));
    }

    /**
     * generate a new quad FloatBuffer (4 * xyz)
     */
    public static ByteBuffer generateQuad(float size) {
        return (toByteBuffer(Quad.make(size)));
    }

    /**
     * generate a new quad FloatBuffer (made with triangles) (6 * xyz)
     */
    public static ByteBuffer generateQuadTriangles(float size) {
        return (toByteBuffer(Quad.makeWithTriangle(size)));
    }

    /**
     * generate a new quad FloatBuffer (made with triangles with uv) (6 * xyzuv)
     */
    public static ByteBuffer generateQuadTrianglesUV(float size) {
        return (toByteBuffer(Quad.makeWithTriangleUV(size)));
    }

    private static ByteBuffer toByteBuffer(float[] vertices) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(vertices.length * 4);
        for (float f : vertices) {
            buffer.putFloat(f);
        }
        buffer.flip();
        return (buffer);
    }
}
