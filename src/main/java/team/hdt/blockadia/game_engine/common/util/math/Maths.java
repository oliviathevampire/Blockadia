package team.hdt.blockadia.game_engine.common.util.math;


import team.hdt.blockadia.game_engine.client.entity.Camera;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class Maths {

    public static float barryCentric(Vectors3f p1, Vectors3f p2, Vectors3f p3, Vectors2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Matrix4fs createTransformationMatrix(Vectors3f translation, float rx, float ry,
                                                       float rz, float scale) {
        Matrix4fs matrix = new Matrix4fs();
        matrix.setIdentity();
        Matrix4fs.translate(translation, matrix, matrix);
        Matrix4fs.rotate((float) Math.toRadians(rx), new Vectors3f(1, 0, 0), matrix, matrix);
        Matrix4fs.rotate((float) Math.toRadians(ry), new Vectors3f(0, 1, 0), matrix, matrix);
        Matrix4fs.rotate((float) Math.toRadians(rz), new Vectors3f(0, 0, 1), matrix, matrix);
        Matrix4fs.scale(new Vectors3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4fs createTransformationMatrix(Vectors2f translation, Vectors2f scale) {
        Matrix4fs matrix = new Matrix4fs();
        matrix.setIdentity();
        Matrix4fs.translate(translation, matrix, matrix);
        Matrix4fs.scale(new Vectors3f(scale.x, scale.y, 1f), matrix, matrix);
        return matrix;
    }

    public static Matrix4fs createViewMatrix(Camera camera) {
        Matrix4fs viewMatrix = new Matrix4fs();
        viewMatrix.setIdentity();
        Matrix4fs.rotate((float) Math.toRadians(camera.getPitch()), new Vectors3f(1, 0, 0), viewMatrix,
                viewMatrix);
        Matrix4fs.rotate((float) Math.toRadians(camera.getYaw()), new Vectors3f(0, 1, 0), viewMatrix, viewMatrix);
        Vectors3f cameraPos = camera.getPosition();
        Vectors3f negativeCameraPos = new Vectors3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4fs.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

    public static float abs(float fValue) {
        if (fValue < 0) {
            return -fValue;
        }
        return fValue;
    }
}
