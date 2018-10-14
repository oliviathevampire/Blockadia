package team.hdt.blockadia.engine.core.util;

import ga.pheonix.utillib.utils.vectors.Vectors2f;
import ga.pheonix.utillib.utils.vectors.Vectors3f;

/*
 * This class is made by HuskyTheArtist
 * the 26.09.2018 at 18.31
 */
public class Matrix4fs extends ga.pheonix.utillib.utils.vectors.Matrix4fs {

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
