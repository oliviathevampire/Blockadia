package team.hdt.blockadia.engine.core.world.misc;

import ga.pheonix.utillib.utils.vectors.Vectors3f;

public class NormalsGenerator {

    public static Vectors3f[][] generateNormals(float[][] heights) {
        Vectors3f[][] normals = new Vectors3f[heights.length][heights.length];
        for (int z = 0; z < normals.length; z++) {
            for (int x = 0; x < normals[z].length; x++) {
                normals[z][x] = calculateNormal(x, z, heights);
            }
        }
        return normals;
    }

    private static Vectors3f calculateNormal(int x, int z, float[][] heights) {
        float heightL = getHeight(x - 1, z, heights);
        float heightR = getHeight(x + 1, z, heights);
        float heightD = getHeight(x, z - 1, heights);
        float heightU = getHeight(x, z + 1, heights);
        Vectors3f normal = new Vectors3f(heightL - heightR, 2f, heightD - heightU);
//        normal.normalise();
        return normal;
    }

    private static float getHeight(int x, int z, float[][] heights) {
        x = x < 0 ? 0 : x;
        z = z < 0 ? 0 : z;
        x = x >= heights.length ? heights.length - 1 : x;
        z = z >= heights.length ? heights.length - 1 : z;
        return heights[z][x];
    }

}
