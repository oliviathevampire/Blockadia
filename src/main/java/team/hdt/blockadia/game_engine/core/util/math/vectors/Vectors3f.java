/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.hdt.blockadia.game_engine.core.util.math.vectors;


import team.hdt.blockadia.game_engine.core.util.math.vectors.interfaces.ReadableVector3f;
import team.hdt.blockadia.game_engine.core.util.math.vectors.interfaces.WritableVector3f;

import java.io.Serializable;
import java.nio.FloatBuffer;

/**
 * @author 326296
 */
public class Vectors3f extends Vectors implements Serializable, ReadableVector3f, WritableVector3f {


    private static final long serialVersionUID = 1L;
    public float x;
    public float y;
    public float z;

    public static Vectors3f xAxis = new Vectors3f(1, 0, 0);
    public static Vectors3f yAxis = new Vectors3f(0, 1, 0);
    public static Vectors3f zAxis = new Vectors3f(0, 0, 1);

    public Vectors3f() {
    }

    public Vectors3f(ReadableVector3f src) {
        set(src);
    }

    public Vectors3f(float x, float y, float z) {
        set(x, y, z);
    }

    public static Vectors3f add(Vectors3f left, Vectors3f right, Vectors3f dest) {
        if (dest == null) {
            return new Vectors3f(left.x + right.x, left.y + right.y, left.z + right.z);
        }
        dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
        return dest;
    }

    public static Vectors3f sub(Vectors3f left, Vectors3f right, Vectors3f dest) {
        if (dest == null) {
            return new Vectors3f(left.x - right.x, left.y - right.y, left.z - right.z);
        }
        dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
        return dest;
    }

    public static Vectors3f cross(Vectors3f left, Vectors3f right, Vectors3f dest) {
        if (dest == null) {
            dest = new Vectors3f();
        }
        dest.set(left.y * right.z - left.z * right.y, right.x * left.z - right.z * left.x, left.x * right.y - left.y * right.x);

        return dest;
    }

    public static float dot(Vectors3f left, Vectors3f right) {
        return left.x * right.x + left.y * right.y + left.z * right.z;
    }

    public static float angle(Vectors3f a, Vectors3f b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1.0F) {
            dls = -1.0F;
        } else if (dls > 1.0F) {
            dls = 1.0F;
        }
        return (float) Math.acos(dls);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vectors3f set(ReadableVector3f src) {
        this.x = src.getX();
        this.y = src.getY();
        this.z = src.getZ();
        return this;
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public Vectors3f translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vectors3f addLocal(Vectors3f vec) {
        if (null == vec) {
            System.out.println("Provided vector is null, null returned.");
            return null;
        }
        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    public Vectors negate() {
        this.x = (-this.x);
        this.y = (-this.y);
        this.z = (-this.z);
        return this;
    }

    public Vectors3f negate(Vectors3f dest) {
        if (dest == null) {
            dest = new Vectors3f();
        }
        dest.x = (-this.x);
        dest.y = (-this.y);
        dest.z = (-this.z);
        return dest;
    }

    public Vectors3f normalise(Vectors3f dest) {
        float l = length();
        if (dest == null) {
            dest = new Vectors3f(this.x / l, this.y / l, this.z / l);
        } else {
            dest.set(this.x / l, this.y / l, this.z / l);
        }
        return dest;
    }

    @Override
    public final float getX() {
        return this.x;
    }

    @Override
    public final void setX(float x) {
        this.x = x;
    }

    @Override
    public final float getY() {
        return this.y;
    }

    @Override
    public final void setY(float y) {
        this.y = y;
    }

    @Override
    public float getZ() {
        return this.z;
    }

    @Override
    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public Vectors load(FloatBuffer buf) {
        this.x = buf.get();
        this.y = buf.get();
        this.z = buf.get();
        return this;
    }

    @Override
    public Vectors store(FloatBuffer buf) {
        buf.put(this.x);
        buf.put(this.y);
        buf.put(this.z);

        return this;
    }

    @Override
    public Vectors scale(float scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;

        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(64);

        sb.append("Vector3f[");
        sb.append(this.x);
        sb.append(", ");
        sb.append(this.y);
        sb.append(", ");
        sb.append(this.z);
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Vectors3f multLocal(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vectors3f subtractLocal(Vectors3f vec) {
        if (null == vec) {
            System.out.println("Provided vector is null, null returned.");
            return null;
        }
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }


}
