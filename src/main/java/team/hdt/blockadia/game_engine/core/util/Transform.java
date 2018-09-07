package team.hdt.blockadia.game_engine.core.util;

import team.hdt.blockadia.game_engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;

public class Transform {
    private Matrix4fs translation;
    private Matrix4fs rotation;
    private Matrix4fs scale;

    public Transform() {
        translation = new Matrix4fs();
        rotation = new Matrix4fs();
        scale = new Matrix4fs();
    }

    public void translate(Vectors3f vec) {
        Matrix4fs.translate(vec, translation, translation);
    }

    public void translate(float x, float y, float z) {
        translate(new Vectors3f(x, y, z));
    }

    public void rotate(Vectors3f rotation) {
        rotate(rotation.x, rotation.y, rotation.z);
    }

    public void rotate(float x, float y, float z) {
        Matrix4fs.rotate((float) Math.toRadians(x), Vectors3f.xAxis, rotation, rotation);
        Matrix4fs.rotate((float) Math.toRadians(y), Vectors3f.yAxis, rotation, rotation);
        Matrix4fs.rotate((float) Math.toRadians(z), Vectors3f.zAxis, rotation, rotation);
    }

    public void scale(Vectors3f vec) {
        Matrix4fs.scale(vec, scale, scale);
    }

    public void scale(float x, float y, float z) {
        scale(new Vectors3f(x, y, z));
    }

    public Matrix4fs getTransform() {
        return Matrix4fs.mul(translation, Matrix4fs.mul(rotation, scale, null), null);
    }
}