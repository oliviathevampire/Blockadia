package team.hdt.blockadia.game_engine.common.util.math.vectors;

import java.io.Serializable;
import java.nio.FloatBuffer;

public class Matrix2fs extends Matrixs implements Serializable {

    private static final long serialVersionUID = 1L;

    public float m00, m01, m10, m11;

    public Matrix2fs() {
        setIdentity();
    }

    public Matrix2fs(Matrix2fs src) {
        load(src);
    }

    public Matrix2fs load(Matrix2fs src) {
        return load(src, this);
    }

    public static Matrix2fs load(Matrix2fs src, Matrix2fs dest) {
        if (dest == null)
            dest = new Matrix2fs();

        dest.m00 = src.m00;
        dest.m01 = src.m01;
        dest.m10 = src.m10;
        dest.m11 = src.m11;

        return dest;
    }

    public Matrixs load(FloatBuffer buf) {

        m00 = buf.get();
        m01 = buf.get();
        m10 = buf.get();
        m11 = buf.get();

        return this;
    }

    public Matrixs loadTranspose(FloatBuffer buf) {

        m00 = buf.get();
        m10 = buf.get();
        m01 = buf.get();
        m11 = buf.get();

        return this;
    }

    public Matrixs store(FloatBuffer buf) {
        buf.put(m00);
        buf.put(m01);
        buf.put(m10);
        buf.put(m11);
        return this;
    }

    public Matrixs storeTranspose(FloatBuffer buf) {
        buf.put(m00);
        buf.put(m10);
        buf.put(m01);
        buf.put(m11);
        return this;
    }

    public static Matrix2fs add(Matrix2fs left, Matrix2fs right, Matrix2fs dest) {
        if (dest == null)
            dest = new Matrix2fs();

        dest.m00 = left.m00 + right.m00;
        dest.m01 = left.m01 + right.m01;
        dest.m10 = left.m10 + right.m10;
        dest.m11 = left.m11 + right.m11;

        return dest;
    }

    public static Matrix2fs sub(Matrix2fs left, Matrix2fs right, Matrix2fs dest) {
        if (dest == null)
            dest = new Matrix2fs();

        dest.m00 = left.m00 - right.m00;
        dest.m01 = left.m01 - right.m01;
        dest.m10 = left.m10 - right.m10;
        dest.m11 = left.m11 - right.m11;

        return dest;
    }

    public static Matrix2fs mul(Matrix2fs left, Matrix2fs right, Matrix2fs dest) {
        if (dest == null)
            dest = new Matrix2fs();

        float m00 = left.m00 * right.m00 + left.m10 * right.m01;
        float m01 = left.m01 * right.m00 + left.m11 * right.m01;
        float m10 = left.m00 * right.m10 + left.m10 * right.m11;
        float m11 = left.m01 * right.m10 + left.m11 * right.m11;

        dest.m00 = m00;
        dest.m01 = m01;
        dest.m10 = m10;
        dest.m11 = m11;

        return dest;
    }

    public static Vectors2f transform(Matrix2fs left, Vectors2f right, Vectors2f dest) {
        if (dest == null)
            dest = new Vectors2f();

        float x = left.m00 * right.x + left.m10 * right.y;
        float y = left.m01 * right.x + left.m11 * right.y;

        dest.x = x;
        dest.y = y;

        return dest;
    }

    public Matrixs transpose() {
        return transpose(this);
    }

    public Matrix2fs transpose(Matrix2fs dest) {
        return transpose(this, dest);
    }

    public static Matrix2fs transpose(Matrix2fs src, Matrix2fs dest) {
        if (dest == null)
            dest = new Matrix2fs();

        float m01 = src.m10;
        float m10 = src.m01;

        dest.m01 = m01;
        dest.m10 = m10;

        return dest;
    }

    public Matrixs invert() {
        return invert(this, this);
    }

    public static Matrix2fs invert(Matrix2fs src, Matrix2fs dest) {
        /*
         *inv(A) = 1/det(A) * adj(A);
         */

        float determinant = src.determinant();
        if (determinant != 0) {
            if (dest == null)
                dest = new Matrix2fs();
            float determinant_inv = 1f/determinant;
            float t00 =  src.m11*determinant_inv;
            float t01 = -src.m01*determinant_inv;
            float t11 =  src.m00*determinant_inv;
            float t10 = -src.m10*determinant_inv;

            dest.m00 = t00;
            dest.m01 = t01;
            dest.m10 = t10;
            dest.m11 = t11;
            return dest;
        } else
            return null;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(m00).append(' ').append(m10).append(' ').append('\n');
        buf.append(m01).append(' ').append(m11).append(' ').append('\n');
        return buf.toString();
    }

    public Matrixs negate() {
        return negate(this);
    }

    public Matrix2fs negate(Matrix2fs dest) {
        return negate(this, dest);
    }

    public static Matrix2fs negate(Matrix2fs src, Matrix2fs dest) {
        if (dest == null)
            dest = new Matrix2fs();

        dest.m00 = -src.m00;
        dest.m01 = -src.m01;
        dest.m10 = -src.m10;
        dest.m11 = -src.m11;

        return dest;
    }

    public Matrixs setIdentity() {
        return setIdentity(this);
    }

    public static Matrix2fs setIdentity(Matrix2fs src) {
        src.m00 = 1.0f;
        src.m01 = 0.0f;
        src.m10 = 0.0f;
        src.m11 = 1.0f;
        return src;
    }

    public Matrixs setZero() {
        return setZero(this);
    }

    public static Matrix2fs setZero(Matrix2fs src) {
        src.m00 = 0.0f;
        src.m01 = 0.0f;
        src.m10 = 0.0f;
        src.m11 = 0.0f;
        return src;
    }

    public float determinant() {
        return m00 * m11 - m01*m10;
    }
}
