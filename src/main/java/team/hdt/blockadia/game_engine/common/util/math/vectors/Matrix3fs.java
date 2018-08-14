package team.hdt.blockadia.game_engine.common.util.math.vectors;

import java.io.Serializable;
import java.nio.FloatBuffer;

public class Matrix3fs extends Matrixs implements Serializable {

    private static final long serialVersionUID = 1L;

    public float m00,
            m01,
            m02,
            m10,
            m11,
            m12,
            m20,
            m21,
            m22;

    public Matrix3fs() {
        super();
        setIdentity();
    }

    public static Matrix3fs load(Matrix3fs src, Matrix3fs dest) {
        if (dest == null)
            dest = new Matrix3fs();

        dest.m00 = src.m00;
        dest.m10 = src.m10;
        dest.m20 = src.m20;
        dest.m01 = src.m01;
        dest.m11 = src.m11;
        dest.m21 = src.m21;
        dest.m02 = src.m02;
        dest.m12 = src.m12;
        dest.m22 = src.m22;

        return dest;
    }

    public static Matrix3fs add(Matrix3fs left, Matrix3fs right, Matrix3fs dest) {
        if (dest == null)
            dest = new Matrix3fs();

        dest.m00 = left.m00 + right.m00;
        dest.m01 = left.m01 + right.m01;
        dest.m02 = left.m02 + right.m02;
        dest.m10 = left.m10 + right.m10;
        dest.m11 = left.m11 + right.m11;
        dest.m12 = left.m12 + right.m12;
        dest.m20 = left.m20 + right.m20;
        dest.m21 = left.m21 + right.m21;
        dest.m22 = left.m22 + right.m22;

        return dest;
    }

    public static Matrix3fs sub(Matrix3fs left, Matrix3fs right, Matrix3fs dest) {
        if (dest == null)
            dest = new Matrix3fs();

        dest.m00 = left.m00 - right.m00;
        dest.m01 = left.m01 - right.m01;
        dest.m02 = left.m02 - right.m02;
        dest.m10 = left.m10 - right.m10;
        dest.m11 = left.m11 - right.m11;
        dest.m12 = left.m12 - right.m12;
        dest.m20 = left.m20 - right.m20;
        dest.m21 = left.m21 - right.m21;
        dest.m22 = left.m22 - right.m22;

        return dest;
    }

    public static Matrix3fs mul(Matrix3fs left, Matrix3fs right, Matrix3fs dest) {
        if (dest == null)
            dest = new Matrix3fs();

        float m00 =
                left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
        float m01 =
                left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
        float m02 =
                left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
        float m10 =
                left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
        float m11 =
                left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
        float m12 =
                left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
        float m20 =
                left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
        float m21 =
                left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
        float m22 =
                left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;

        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;

        return dest;
    }

    public static Vectors3f transform(Matrix3fs left, Vectors3f right, Vectors3f dest) {
        if (dest == null)
            dest = new Vectors3f();

        float x = left.m00 * right.x + left.m10 * right.y + left.m20 * right.z;
        float y = left.m01 * right.x + left.m11 * right.y + left.m21 * right.z;
        float z = left.m02 * right.x + left.m12 * right.y + left.m22 * right.z;

        dest.x = x;
        dest.y = y;
        dest.z = z;

        return dest;
    }

    public static Matrix3fs transpose(Matrix3fs src, Matrix3fs dest) {
        if (dest == null)
            dest = new Matrix3fs();
        float m00 = src.m00;
        float m01 = src.m10;
        float m02 = src.m20;
        float m10 = src.m01;
        float m11 = src.m11;
        float m12 = src.m21;
        float m20 = src.m02;
        float m21 = src.m12;
        float m22 = src.m22;

        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;
        return dest;
    }

    public static Matrix3fs invert(Matrix3fs src, Matrix3fs dest) {
        float determinant = src.determinant();

        if (determinant != 0) {
            if (dest == null)
                dest = new Matrix3fs();
            /* do it the ordinary way
             *
             * inv(A) = 1/det(A) * adj(T), where adj(T) = transpose(Conjugate Matrix)
             *
             * m00 m01 m02
             * m10 m11 m12
             * m20 m21 m22
             */
            float determinant_inv = 1f / determinant;

            // get the conjugate matrix
            float t00 = src.m11 * src.m22 - src.m12 * src.m21;
            float t01 = -src.m10 * src.m22 + src.m12 * src.m20;
            float t02 = src.m10 * src.m21 - src.m11 * src.m20;
            float t10 = -src.m01 * src.m22 + src.m02 * src.m21;
            float t11 = src.m00 * src.m22 - src.m02 * src.m20;
            float t12 = -src.m00 * src.m21 + src.m01 * src.m20;
            float t20 = src.m01 * src.m12 - src.m02 * src.m11;
            float t21 = -src.m00 * src.m12 + src.m02 * src.m10;
            float t22 = src.m00 * src.m11 - src.m01 * src.m10;

            dest.m00 = t00 * determinant_inv;
            dest.m11 = t11 * determinant_inv;
            dest.m22 = t22 * determinant_inv;
            dest.m01 = t10 * determinant_inv;
            dest.m10 = t01 * determinant_inv;
            dest.m20 = t02 * determinant_inv;
            dest.m02 = t20 * determinant_inv;
            dest.m12 = t21 * determinant_inv;
            dest.m21 = t12 * determinant_inv;
            return dest;
        } else
            return null;
    }

    public static Matrix3fs negate(Matrix3fs src, Matrix3fs dest) {
        if (dest == null)
            dest = new Matrix3fs();

        dest.m00 = -src.m00;
        dest.m01 = -src.m02;
        dest.m02 = -src.m01;
        dest.m10 = -src.m10;
        dest.m11 = -src.m12;
        dest.m12 = -src.m11;
        dest.m20 = -src.m20;
        dest.m21 = -src.m22;
        dest.m22 = -src.m21;
        return dest;
    }

    public static Matrix3fs setIdentity(Matrix3fs m) {
        m.m00 = 1.0f;
        m.m01 = 0.0f;
        m.m02 = 0.0f;
        m.m10 = 0.0f;
        m.m11 = 1.0f;
        m.m12 = 0.0f;
        m.m20 = 0.0f;
        m.m21 = 0.0f;
        m.m22 = 1.0f;
        return m;
    }

    public static Matrix3fs setZero(Matrix3fs m) {
        m.m00 = 0.0f;
        m.m01 = 0.0f;
        m.m02 = 0.0f;
        m.m10 = 0.0f;
        m.m11 = 0.0f;
        m.m12 = 0.0f;
        m.m20 = 0.0f;
        m.m21 = 0.0f;
        m.m22 = 0.0f;
        return m;
    }

    public Matrix3fs load(Matrix3fs src) {
        return load(src, this);
    }

    public Matrixs load(FloatBuffer buf) {

        m00 = buf.get();
        m01 = buf.get();
        m02 = buf.get();
        m10 = buf.get();
        m11 = buf.get();
        m12 = buf.get();
        m20 = buf.get();
        m21 = buf.get();
        m22 = buf.get();

        return this;
    }

    public Matrixs loadTranspose(FloatBuffer buf) {

        m00 = buf.get();
        m10 = buf.get();
        m20 = buf.get();
        m01 = buf.get();
        m11 = buf.get();
        m21 = buf.get();
        m02 = buf.get();
        m12 = buf.get();
        m22 = buf.get();

        return this;
    }

    public Matrixs store(FloatBuffer buf) {
        buf.put(m00);
        buf.put(m01);
        buf.put(m02);
        buf.put(m10);
        buf.put(m11);
        buf.put(m12);
        buf.put(m20);
        buf.put(m21);
        buf.put(m22);
        return this;
    }

    public Matrixs storeTranspose(FloatBuffer buf) {
        buf.put(m00);
        buf.put(m10);
        buf.put(m20);
        buf.put(m01);
        buf.put(m11);
        buf.put(m21);
        buf.put(m02);
        buf.put(m12);
        buf.put(m22);
        return this;
    }

    public Matrixs transpose() {
        return transpose(this, this);
    }

    public Matrix3fs transpose(Matrix3fs dest) {
        return transpose(this, dest);
    }

    public float determinant() {
        float f =
                m00 * (m11 * m22 - m12 * m21)
                        + m01 * (m12 * m20 - m10 * m22)
                        + m02 * (m10 * m21 - m11 * m20);
        return f;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(m00).append(' ').append(m10).append(' ').append(m20).append(' ').append('\n');
        buf.append(m01).append(' ').append(m11).append(' ').append(m21).append(' ').append('\n');
        buf.append(m02).append(' ').append(m12).append(' ').append(m22).append(' ').append('\n');
        return buf.toString();
    }

    public Matrixs invert() {
        return invert(this, this);
    }

    public Matrixs negate() {
        return negate(this);
    }

    public Matrix3fs negate(Matrix3fs dest) {
        return negate(this, dest);
    }

    public Matrixs setIdentity() {
        return setIdentity(this);
    }

    public Matrixs setZero() {
        return setZero(this);
    }
}
