package team.hdt.blockadia.engine_rewrite.core.utils;

import ga.pheonix.utillib.utils.vectors.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Matrix4fs {
    public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;

    public Matrix4fs() {
    }

    public Matrix4fs(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    private Matrix4fs thisOrNew() {
        return this;
    }

    public float m00() {
        return this.m00;
    }

    public float m01() {
        return this.m01;
    }

    public float m02() {
        return this.m02;
    }

    public float m03() {
        return this.m03;
    }

    public float m10() {
        return this.m10;
    }

    public float m11() {
        return this.m11;
    }

    public float m12() {
        return this.m12;
    }

    public float m13() {
        return this.m13;
    }

    public float m20() {
        return this.m20;
    }

    public float m21() {
        return this.m21;
    }

    public float m22() {
        return this.m22;
    }

    public float m23() {
        return this.m23;
    }

    public float m30() {
        return this.m30;
    }

    public float m31() {
        return this.m31;
    }

    public float m32() {
        return this.m32;
    }

    public float m33() {
        return this.m33;
    }

    public Matrix4fs m00(float m00) {
        this.m00 = m00;
        return this;
    }

    public Matrix4fs m01(float m01) {
        this.m01 = m01;
        return this;
    }

    public Matrix4fs m02(float m02) {
        this.m02 = m02;
        return this;
    }

    public Matrix4fs m03(float m03) {
        this.m03 = m03;
        return this;
    }

    public Matrix4fs m10(float m10) {
        this.m10 = m10;
        return this;
    }

    public Matrix4fs m11(float m11) {
        this.m11 = m11;
        return this;
    }

    public Matrix4fs m12(float m12) {
        this.m12 = m12;
        return this;
    }

    public Matrix4fs m13(float m13) {
        this.m13 = m13;
        return this;
    }

    public Matrix4fs m20(float m20) {
        this.m20 = m20;
        return this;
    }

    public Matrix4fs m21(float m21) {
        this.m21 = m21;
        return this;
    }

    public Matrix4fs m22(float m22) {
        this.m22 = m22;
        return this;
    }

    public Matrix4fs m23(float m23) {
        this.m23 = m23;
        return this;
    }

    public Matrix4fs m30(float m30) {
        this.m30 = m30;
        return this;
    }

    public Matrix4fs m31(float m31) {
        this.m31 = m31;
        return this;
    }

    public Matrix4fs m32(float m32) {
        this.m32 = m32;
        return this;
    }

    public Matrix4fs m33(float m33) {
        this.m33 = m33;
        return this;
    }

    public Matrix4fs mul(Matrix3x2fc right) {
        return this.mul(right, this.thisOrNew());
    }

    public Matrix4fs mul(Matrix3x2fc right, Matrix4fs dest) {
        float nm00 = this.m00 * right.m00() + this.m10 * right.m01();
        float nm01 = this.m01 * right.m00() + this.m11 * right.m01();
        float nm02 = this.m02 * right.m00() + this.m12 * right.m01();
        float nm03 = this.m03 * right.m00() + this.m13 * right.m01();
        float nm10 = this.m00 * right.m10() + this.m10 * right.m11();
        float nm11 = this.m01 * right.m10() + this.m11 * right.m11();
        float nm12 = this.m02 * right.m10() + this.m12 * right.m11();
        float nm13 = this.m03 * right.m10() + this.m13 * right.m11();
        float nm30 = this.m00 * right.m20() + this.m10 * right.m21() + this.m30;
        float nm31 = this.m01 * right.m20() + this.m11 * right.m21() + this.m31;
        float nm32 = this.m02 * right.m20() + this.m12 * right.m21() + this.m32;
        float nm33 = this.m03 * right.m20() + this.m13 * right.m21() + this.m33;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m30 = nm30;
        dest.m31 = nm31;
        dest.m32 = nm32;
        dest.m33 = nm33;
        return dest;
    }

    public Matrix4fs set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        return this;
    }

    public void setVector4fc(Vector4fc col0, Vector4fc col1, Vector4fc col2, Vector4fc col3) {
        this.m00 = col0.x();
        this.m01 = col0.y();
        this.m02 = col0.z();
        this.m03 = col0.w();
        this.m10 = col1.x();
        this.m11 = col1.y();
        this.m12 = col1.z();
        this.m13 = col1.w();
        this.m20 = col2.x();
        this.m21 = col2.y();
        this.m22 = col2.z();
        this.m23 = col2.w();
        this.m30 = col3.x();
        this.m31 = col3.y();
        this.m32 = col3.z();
        this.m33 = col3.w();
    }

    public Matrix4fs transposeGeneric(Matrix4fs dest) {
        float nm00 = this.m00;
        float nm01 = this.m10;
        float nm02 = this.m20;
        float nm03 = this.m30;
        float nm10 = this.m01;
        float nm11 = this.m11;
        float nm12 = this.m21;
        float nm13 = this.m31;
        float nm20 = this.m02;
        float nm21 = this.m12;
        float nm22 = this.m22;
        float nm23 = this.m32;
        float nm30 = this.m03;
        float nm31 = this.m13;
        float nm32 = this.m23;
        float nm33 = this.m33;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m22 = nm22;
        dest.m23 = nm23;
        dest.m30 = nm30;
        dest.m31 = nm31;
        dest.m32 = nm32;
        dest.m33 = nm33;
        return dest;
    }

    public Matrix4fs transpose3x3() {
        return this.transpose3x3(this.thisOrNew());
    }

    public Matrix4fs transpose3x3(Matrix4fs dest) {
        float nm00 = this.m00;
        float nm01 = this.m10;
        float nm02 = this.m20;
        float nm10 = this.m01;
        float nm11 = this.m11;
        float nm12 = this.m21;
        float nm20 = this.m02;
        float nm21 = this.m12;
        float nm22 = this.m22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m22 = nm22;
        return dest;
    }

    public Matrix3fs transpose3x3(Matrix3fs dest) {
        dest.m00(this.m00);
        dest.m01(this.m10);
        dest.m02(this.m20);
        dest.m10(this.m01);
        dest.m11(this.m11);
        dest.m12(this.m21);
        dest.m20(this.m02);
        dest.m21(this.m12);
        dest.m22(this.m22);
        return dest;
    }

    public Vectors3f getTranslation(Vectors3f dest) {
        dest.x = this.m30;
        dest.y = this.m31;
        dest.z = this.m32;
        return dest;
    }

    public Vectors3f getScale(Vectors3f dest) {
        dest.x = (float) Math.sqrt((double) (this.m00 * this.m00 + this.m01 * this.m01 + this.m02 * this.m02));
        dest.y = (float) Math.sqrt((double) (this.m10 * this.m10 + this.m11 * this.m11 + this.m12 * this.m12));
        dest.z = (float) Math.sqrt((double) (this.m20 * this.m20 + this.m21 * this.m21 + this.m22 * this.m22));
        return dest;
    }

    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        String str = this.toString(formatter);
        StringBuffer res = new StringBuffer();
        int eIndex = -2147483648;

        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c == 'E') {
                eIndex = i;
            } else {
                if (c == ' ' && eIndex == i - 1) {
                    res.append('+');
                    continue;
                }

                if (Character.isDigit(c) && eIndex == i - 1) {
                    res.append('+');
                }
            }

            res.append(c);
        }

        return res.toString();
    }

    public String toString(NumberFormat formatter) {
        formatter.format((double) this.m00);
        formatter.format((double) this.m10);
        formatter.format((double) this.m20);
        formatter.format((double) this.m30);
        formatter.format((double) this.m01);
        formatter.format((double) this.m11);
        formatter.format((double) this.m21);
        formatter.format((double) this.m31);
        formatter.format((double) this.m02);
        formatter.format((double) this.m12);
        formatter.format((double) this.m22);
        formatter.format((double) this.m32);
        formatter.format((double) this.m03);
        formatter.format((double) this.m13);
        formatter.format((double) this.m23);
        return formatter.format((double) this.m33);
    }

    public void set3x3Matrix3fsc(Matrix3fc mat) {
        this.m00 = mat.m00();
        this.m01 = mat.m01();
        this.m02 = mat.m02();
        this.m10 = mat.m10();
        this.m11 = mat.m11();
        this.m12 = mat.m12();
        this.m20 = mat.m20();
        this.m21 = mat.m21();
        this.m22 = mat.m22();
    }

    public Vectors4f unproject(float winX, float winY, float winZ, int[] viewport, Vectors4f dest) {
        float a = this.m00 * this.m11 - this.m01 * this.m10;
        float b = this.m00 * this.m12 - this.m02 * this.m10;
        float c = this.m00 * this.m13 - this.m03 * this.m10;
        float d = this.m01 * this.m12 - this.m02 * this.m11;
        float e = this.m01 * this.m13 - this.m03 * this.m11;
        float f = this.m02 * this.m13 - this.m03 * this.m12;
        float g = this.m20 * this.m31 - this.m21 * this.m30;
        float h = this.m20 * this.m32 - this.m22 * this.m30;
        float i = this.m20 * this.m33 - this.m23 * this.m30;
        float j = this.m21 * this.m32 - this.m22 * this.m31;
        float k = this.m21 * this.m33 - this.m23 * this.m31;
        float l = this.m22 * this.m33 - this.m23 * this.m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0F / det;
        float im00 = (this.m11 * l - this.m12 * k + this.m13 * j) * det;
        float im01 = (-this.m01 * l + this.m02 * k - this.m03 * j) * det;
        float im02 = (this.m31 * f - this.m32 * e + this.m33 * d) * det;
        float im03 = (-this.m21 * f + this.m22 * e - this.m23 * d) * det;
        float im10 = (-this.m10 * l + this.m12 * i - this.m13 * h) * det;
        float im11 = (this.m00 * l - this.m02 * i + this.m03 * h) * det;
        float im12 = (-this.m30 * f + this.m32 * c - this.m33 * b) * det;
        float im13 = (this.m20 * f - this.m22 * c + this.m23 * b) * det;
        float im20 = (this.m10 * k - this.m11 * i + this.m13 * g) * det;
        float im21 = (-this.m00 * k + this.m01 * i - this.m03 * g) * det;
        float im22 = (this.m30 * e - this.m31 * c + this.m33 * a) * det;
        float im23 = (-this.m20 * e + this.m21 * c - this.m23 * a) * det;
        float im30 = (-this.m10 * j + this.m11 * h - this.m12 * g) * det;
        float im31 = (this.m00 * j - this.m01 * h + this.m02 * g) * det;
        float im32 = (-this.m30 * d + this.m31 * b - this.m32 * a) * det;
        float im33 = (this.m20 * d - this.m21 * b + this.m22 * a) * det;
        float ndcX = (winX - (float) viewport[0]) / (float) viewport[2] * 2.0F - 1.0F;
        float ndcY = (winY - (float) viewport[1]) / (float) viewport[3] * 2.0F - 1.0F;
        float ndcZ = winZ + winZ - 1.0F;
        float invW = 1.0F / (im03 * ndcX + im13 * ndcY + im23 * ndcZ + im33);
        dest.x = (im00 * ndcX + im10 * ndcY + im20 * ndcZ + im30) * invW;
        dest.y = (im01 * ndcX + im11 * ndcY + im21 * ndcZ + im31) * invW;
        dest.z = (im02 * ndcX + im12 * ndcY + im22 * ndcZ + im32) * invW;
        dest.w = 1.0F;
        return dest;
    }

    public Vectors4f project(float x, float y, float z, int[] viewport, Vectors4f winCoordsDest) {
        float invW = 1.0F / (this.m03 * x + this.m13 * y + this.m23 * z + this.m33);
        float nx = (this.m00 * x + this.m10 * y + this.m20 * z + this.m30) * invW;
        float ny = (this.m01 * x + this.m11 * y + this.m21 * z + this.m31) * invW;
        float nz = (this.m02 * x + this.m12 * y + this.m22 * z + this.m32) * invW;
        winCoordsDest.x = (nx * 0.5F + 0.5F) * (float) viewport[2] + (float) viewport[0];
        winCoordsDest.y = (ny * 0.5F + 0.5F) * (float) viewport[3] + (float) viewport[1];
        winCoordsDest.z = (1.0F + nz) * 0.5F;
        winCoordsDest.w = 1.0F;
        return winCoordsDest;
    }

    public Vectors3f project(float x, float y, float z, int[] viewport, Vectors3f winCoordsDest) {
        float invW = 1.0F / (this.m03 * x + this.m13 * y + this.m23 * z + this.m33);
        float nx = (this.m00 * x + this.m10 * y + this.m20 * z + this.m30) * invW;
        float ny = (this.m01 * x + this.m11 * y + this.m21 * z + this.m31) * invW;
        float nz = (this.m02 * x + this.m12 * y + this.m22 * z + this.m32) * invW;
        winCoordsDest.x = (nx * 0.5F + 0.5F) * (float) viewport[2] + (float) viewport[0];
        winCoordsDest.y = (ny * 0.5F + 0.5F) * (float) viewport[3] + (float) viewport[1];
        winCoordsDest.z = (1.0F + nz) * 0.5F;
        return winCoordsDest;
    }

    public float perspectiveFov() {
        float n1x = this.m03 + this.m01;
        float n1y = this.m13 + this.m11;
        float n1z = this.m23 + this.m21;
        float n2x = this.m01 - this.m03;
        float n2y = this.m11 - this.m13;
        float n2z = this.m21 - this.m23;
        float n1len = (float) Math.sqrt((double) (n1x * n1x + n1y * n1y + n1z * n1z));
        float n2len = (float) Math.sqrt((double) (n2x * n2x + n2y * n2y + n2z * n2z));
        return (float) Math.acos((double) ((n1x * n2x + n1y * n2y + n1z * n2z) / (n1len * n2len)));
    }

    public float perspectiveNear() {
        return this.m32 / (this.m23 + this.m22);
    }

    public float perspectiveFar() {
        return this.m32 / (this.m22 - this.m23);
    }

    public Vectors3f normalizedPositiveZ(Vectors3f dir) {
        dir.x = this.m02;
        dir.y = this.m12;
        dir.z = this.m22;
        return dir;
    }

    public Vectors3f normalizedPositiveX(Vectors3f dir) {
        dir.x = this.m00;
        dir.y = this.m10;
        dir.z = this.m20;
        return dir;
    }

    public Vectors3f normalizedPositiveY(Vectors3f dir) {
        dir.x = this.m01;
        dir.y = this.m11;
        dir.z = this.m21;
        return dir;
    }

    public int hashCode() {
        int result = 31 * 1 + Float.floatToIntBits(this.m00);
        result = 31 * result + Float.floatToIntBits(this.m01);
        result = 31 * result + Float.floatToIntBits(this.m02);
        result = 31 * result + Float.floatToIntBits(this.m03);
        result = 31 * result + Float.floatToIntBits(this.m10);
        result = 31 * result + Float.floatToIntBits(this.m11);
        result = 31 * result + Float.floatToIntBits(this.m12);
        result = 31 * result + Float.floatToIntBits(this.m13);
        result = 31 * result + Float.floatToIntBits(this.m20);
        result = 31 * result + Float.floatToIntBits(this.m21);
        result = 31 * result + Float.floatToIntBits(this.m22);
        result = 31 * result + Float.floatToIntBits(this.m23);
        result = 31 * result + Float.floatToIntBits(this.m30);
        result = 31 * result + Float.floatToIntBits(this.m31);
        result = 31 * result + Float.floatToIntBits(this.m32);
        result = 31 * result + Float.floatToIntBits(this.m33);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof Matrix4fs)) {
            return false;
        } else {
            Matrix4fc other = (Matrix4fc) obj;
            if (Float.floatToIntBits(this.m00) != Float.floatToIntBits(other.m00())) {
                return false;
            } else if (Float.floatToIntBits(this.m01) != Float.floatToIntBits(other.m01())) {
                return false;
            } else if (Float.floatToIntBits(this.m02) != Float.floatToIntBits(other.m02())) {
                return false;
            } else if (Float.floatToIntBits(this.m03) != Float.floatToIntBits(other.m03())) {
                return false;
            } else if (Float.floatToIntBits(this.m10) != Float.floatToIntBits(other.m10())) {
                return false;
            } else if (Float.floatToIntBits(this.m11) != Float.floatToIntBits(other.m11())) {
                return false;
            } else if (Float.floatToIntBits(this.m12) != Float.floatToIntBits(other.m12())) {
                return false;
            } else if (Float.floatToIntBits(this.m13) != Float.floatToIntBits(other.m13())) {
                return false;
            } else if (Float.floatToIntBits(this.m20) != Float.floatToIntBits(other.m20())) {
                return false;
            } else if (Float.floatToIntBits(this.m21) != Float.floatToIntBits(other.m21())) {
                return false;
            } else if (Float.floatToIntBits(this.m22) != Float.floatToIntBits(other.m22())) {
                return false;
            } else if (Float.floatToIntBits(this.m23) != Float.floatToIntBits(other.m23())) {
                return false;
            } else if (Float.floatToIntBits(this.m30) != Float.floatToIntBits(other.m30())) {
                return false;
            } else if (Float.floatToIntBits(this.m31) != Float.floatToIntBits(other.m31())) {
                return false;
            } else if (Float.floatToIntBits(this.m32) != Float.floatToIntBits(other.m32())) {
                return false;
            } else {
                return Float.floatToIntBits(this.m33) == Float.floatToIntBits(other.m33());
            }
        }
    }

    public boolean isAffine() {
        return this.m03 == 0.0F && this.m13 == 0.0F && this.m23 == 0.0F && this.m33 == 1.0F;
    }
    

    public static Matrix4fs rotate(float angle, Vectors3f axis, Matrix4fs src, Matrix4fs dest) {
        if (dest == null) {
            dest = new Matrix4fs();
        }
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0F - c;
        float xy = axis.x * axis.y;
        float yz = axis.y * axis.z;
        float xz = axis.x * axis.z;
        float xs = axis.x * s;
        float ys = axis.y * s;
        float zs = axis.z * s;

        float f00 = axis.x * axis.x * oneminusc + c;
        float f01 = xy * oneminusc + zs;
        float f02 = xz * oneminusc - ys;

        float f10 = xy * oneminusc - zs;
        float f11 = axis.y * axis.y * oneminusc + c;
        float f12 = yz * oneminusc + xs;

        float f20 = xz * oneminusc + ys;
        float f21 = yz * oneminusc - xs;
        float f22 = axis.z * axis.z * oneminusc + c;

        float t00 = src.m00() * f00 + src.m10() * f01 + src.m20() * f02;
        float t01 = src.m01() * f00 + src.m11() * f01 + src.m21() * f02;
        float t02 = src.m02() * f00 + src.m12() * f01 + src.m22() * f02;
        float t03 = src.m03() * f00 + src.m13() * f01 + src.m23() * f02;
        float t10 = src.m00() * f10 + src.m10() * f11 + src.m20() * f12;
        float t11 = src.m01() * f10 + src.m11() * f11 + src.m21() * f12;
        float t12 = src.m02() * f10 + src.m12() * f11 + src.m22() * f12;
        float t13 = src.m03() * f10 + src.m13() * f11 + src.m23() * f12;
        dest.m20((src.m00() * f20 + src.m10() * f21 + src.m20() * f22));
        dest.m21((src.m01() * f20 + src.m11() * f21 + src.m21() * f22));
        dest.m22((src.m02() * f20 + src.m12() * f21 + src.m22() * f22));
        dest.m23((src.m03() * f20 + src.m13() * f21 + src.m23() * f22));
        dest.m00(t00);
        dest.m01(t01);
        dest.m02(t02);
        dest.m03(t03);
        dest.m10(t10);
        dest.m11(t11);
        dest.m12(t12);
        dest.m13(t13);
        return dest;
    }

    public static Matrix4fs translate(Vectors3f vec, Matrix4fs src, Matrix4fs dest) {
        if (dest == null) {
            dest = new Matrix4fs();
        }
        dest.m30(src.m00() *  vec.x + src.m10() *  vec.y + src.m20() *  vec.z);
        dest.m31(src.m01() *  vec.x + src.m11() *  vec.y + src.m21() *  vec.z);
        dest.m32(src.m02() *  vec.x + src.m12() *  vec.y + src.m22() *  vec.z);
        dest.m33(src.m03() *  vec.x + src.m13() *  vec.y + src.m23() *  vec.z);

        return dest;
    }

    public static Matrix4fs translate(Vectors2f vec, Matrix4fs src, Matrix4fs dest) {
        if (dest == null) {
            dest = new Matrix4fs();
        }
        dest.m30(src.m00() *  vec.x + src.m10() *  vec.y);
        dest.m31(src.m01() *  vec.x + src.m11() *  vec.y);
        dest.m32(src.m02() *  vec.x + src.m12() *  vec.y);
        dest.m33(src.m03() *  vec.x + src.m13() *  vec.y);

        return dest;
    }

}
