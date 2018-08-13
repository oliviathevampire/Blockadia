package team.hdt.blockadia.game_engine.common.util.math.vectors;

import team.hdt.blockadia.game_engine.common.util.math.vectors.interfaces.ReadableVector4f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.interfaces.WritableVector4f;

import java.io.Serializable;
import java.nio.FloatBuffer;

public class Vectors4f
        extends Vectors
        implements Serializable, ReadableVector4f, WritableVector4f {
    private static final long serialVersionUID = 1L;
    public float x;
    public float y;
    public float z;
    public float w;

    public Vectors4f() {
    }

    public Vectors4f(ReadableVector4f src) {
        set(src);
    }

    public Vectors4f(float x, float y, float z, float w) {
        set(x, y, z, w);
    }

    public static Vectors4f add(Vectors4f left, Vectors4f right, Vectors4f dest) {
        if (dest == null) {
            return new Vectors4f(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
        }
        dest.set(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
        return dest;
    }

    public static Vectors4f sub(Vectors4f left, Vectors4f right, Vectors4f dest) {
        if (dest == null) {
            return new Vectors4f(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
        }
        dest.set(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
        return dest;
    }

    public static float dot(Vectors4f left, Vectors4f right) {
        return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
    }

    public static float angle(Vectors4f a, Vectors4f b) {
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

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vectors4f set(ReadableVector4f src) {
        this.x = src.getX();
        this.y = src.getY();
        this.z = src.getZ();
        this.w = src.getW();
        return this;
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public Vectors4f translate(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public Vectors negate() {
        this.x = (-this.x);
        this.y = (-this.y);
        this.z = (-this.z);
        this.w = (-this.w);
        return this;
    }

    public Vectors4f negate(Vectors4f dest) {
        if (dest == null) {
            dest = new Vectors4f();
        }
        dest.x = (-this.x);
        dest.y = (-this.y);
        dest.z = (-this.z);
        dest.w = (-this.w);
        return dest;
    }

    public Vectors4f normalise(Vectors4f dest) {
        float l = length();
        if (dest == null) {
            dest = new Vectors4f(this.x / l, this.y / l, this.z / l, this.w / l);
        } else {
            dest.set(this.x / l, this.y / l, this.z / l, this.w / l);
        }
        return dest;
    }

    public Vectors load(FloatBuffer buf) {
        this.x = buf.get();
        this.y = buf.get();
        this.z = buf.get();
        this.w = buf.get();
        return this;
    }

    public Vectors scale(float scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        this.w *= scale;
        return this;
    }

    public Vectors store(FloatBuffer buf) {
        buf.put(this.x);
        buf.put(this.y);
        buf.put(this.z);
        buf.put(this.w);

        return this;
    }

    public String toString() {
        return "Vector4f: " + this.x + " " + this.y + " " + this.z + " " + this.w;
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return this.w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vectors4f other = (Vectors4f) obj;
        if ((this.x == other.x) && (this.y == other.y) && (this.z == other.z) && (this.w == other.w)) {
            return true;
        }
        return false;
    }
}
