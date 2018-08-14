package team.hdt.blockadia.game_engine.client.skybox;

import team.hdt.blockadia.game_engine.common.Loader;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;

public class CurvedMeshGenerator {

    private final int segmentCount;
    private final float segmentTheta;
    private final float factor;

    public CurvedMeshGenerator(int segmentCount, float totalAngle) {
        this.segmentCount = segmentCount;
        this.segmentTheta = totalAngle / segmentCount;
        this.factor = 1f / totalAngle;
    }

    public int generateMeshVao() {
        Vectors2f[] xzPositions = getVertexPositions();
        float[] vertices = new float[xzPositions.length * 2 * 3];
        int pointer = 0;
        for (Vectors2f pos : xzPositions) {
            pointer = storeVertex(pos, 1, vertices, pointer);
            pointer = storeVertex(pos, -1, vertices, pointer);
        }
        return Loader.createInterleavedVAO(xzPositions.length * 2, vertices);
    }

    private int storeVertex(Vectors2f pos, float height, float[] vertices, int pointer) {
        vertices[pointer++] = pos.x * factor;
        vertices[pointer++] = height;
        vertices[pointer++] = pos.y * factor;
        return pointer;
    }

    private Vectors2f[] getVertexPositions() {
        float startPoint = segmentCount / 2f;
        float startingTheta = -startPoint * segmentTheta;
        Vectors2f[] points = new Vectors2f[segmentCount + 1];
        for (int i = 0; i < points.length; i++) {
            points[i] = pointOnCircle(startingTheta + segmentTheta * i);
        }
        return points;
    }

    private Vectors2f pointOnCircle(float theta) {
        float x = (float) Math.sin(theta);
        float z = (float) -Math.cos(theta) + 1;
        return new Vectors2f(x, z);
    }

}
