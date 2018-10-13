package team.hdt.blockadia.engine.core.world.misc;

import ga.pheonix.utillib.utils.Maths;
import ga.pheonix.utillib.utils.vectors.Vectors2f;
import ga.pheonix.utillib.utils.vectors.Vectors3f;

public class HeightFinder {

    private float gridSquareSize;
    private float[][] heights;

    public HeightFinder(float[][] heights, float gridSize) {
        this.heights = heights;
        gridSquareSize = gridSize / (float) (heights.length - 1);
    }

    public float getHeight(float x, float z) {
        VertexQuad quad = calculateVertexQuad(x, z);
        if (quad == null) {
            return 0;
        }
        Vectors2f gridCoords = calculateQuadCoords(x, z);
        VertexTriangle triangle = quad.getTriangle(gridCoords.x, gridCoords.y);
        return Maths.barryCentric(triangle.p0, triangle.p1, triangle.p2, gridCoords);
    }

    private VertexQuad calculateVertexQuad(float x, float z) {
        int gridX = (int) (x / gridSquareSize);
        int gridZ = (int) (z / gridSquareSize);
        if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
            return null;
        }
        return new VertexQuad(gridX, gridZ);
    }

    private Vectors2f calculateQuadCoords(float x, float z) {
        float xCoord = (x % gridSquareSize) / gridSquareSize;
        float zCoord = (z % gridSquareSize) / gridSquareSize;
        return new Vectors2f(xCoord, zCoord);
    }

    private class VertexQuad {

        private final Vectors3f topLeft, topRight, bottomLeft, bottomRight;
        private final boolean rightHanded;

        private VertexQuad(int gridX, int gridZ) {
            topLeft = new Vectors3f(0, heights[gridZ][gridX], 0);
            topRight = new Vectors3f(1, heights[gridZ][gridX + 1], 0);
            bottomLeft = new Vectors3f(0, heights[gridZ + 1][gridX], 1);
            bottomRight = new Vectors3f(1, heights[gridZ + 1][gridX + 1], 1);
            rightHanded = (gridX + gridZ) % 2 != 0;
        }

        private VertexTriangle getTriangle(float xCoord, float zCoord) {
            if (rightHanded) {
                if (xCoord > zCoord) {
                    return new VertexTriangle(topLeft, bottomRight, topRight);
                } else {
                    return new VertexTriangle(topLeft, bottomLeft, bottomRight);
                }
            } else {
                if (xCoord < 1f - zCoord) {
                    return new VertexTriangle(topLeft, bottomLeft, topRight);
                } else {
                    return new VertexTriangle(topRight, bottomLeft, bottomRight);
                }
            }
        }
    }

    private class VertexTriangle {

        private final Vectors3f p0, p1, p2;

        private VertexTriangle(Vectors3f p0, Vectors3f p1, Vectors3f p2) {
            this.p0 = p0;
            this.p1 = p1;
            this.p2 = p2;
        }

    }

}
