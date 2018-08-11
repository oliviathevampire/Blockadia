/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.hdt.sandboxgame.game_engine.common.util.math.vectors;


import team.hdt.sandboxgame.game_engine.common.util.math.vectors.interfaces.ReadableVector2f;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.interfaces.WritableVector2f;

import java.io.Serializable;
import java.nio.FloatBuffer;


/**
 * @author 326296
 */
public class Vectors2f extends Vectors implements Serializable, ReadableVector2f, WritableVector2f {
    private static final long serialVersionUID = 1L;
    public float x;
    public float y;

    public Vectors2f() {
    }

    public Vectors2f(ReadableVector2f src) {
        set(src);
    }

    public Vectors2f(float x, float y) {
        set(x, y);
    }

    public static float dot(Vectors2f left, Vectors2f right) {
        return left.x * right.x + left.y * right.y;
    }

    public static float angle(Vectors2f a, Vectors2f b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1.0F) {
            dls = -1.0F;
        } else if (dls > 1.0F) {
            dls = 1.0F;
        }
        return (float) Math.acos(dls);
    }

    public static Vectors2f add(Vectors2f left, Vectors2f right, Vectors2f dest) {
        if (dest == null) {
            return new Vectors2f(left.x + right.x, left.y + right.y);
        }
        dest.set(left.x + right.x, left.y + right.y);
        return dest;
    }

    public static Vectors2f sub(Vectors2f left, Vectors2f right, Vectors2f dest) {
        if (dest == null) {
            return new Vectors2f(left.x - right.x, left.y - right.y);
        }
        dest.set(left.x - right.x, left.y - right.y);
        return dest;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vectors2f set(ReadableVector2f src) {
        this.x = src.getX();
        this.y = src.getY();
        return this;
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public Vectors2f translate(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vectors negate() {
        this.x = (-this.x);
        this.y = (-this.y);
        return this;
    }

    public Vectors2f negate(Vectors2f dest) {
        if (dest == null) {
            dest = new Vectors2f();
        }
        dest.x = (-this.x);
        dest.y = (-this.y);
        return dest;
    }

    public Vectors2f normalise(Vectors2f dest) {
        float l = length();
        if (dest == null) {
            dest = new Vectors2f(this.x / l, this.y / l);
        } else {
            dest.set(this.x / l, this.y / l);
        }
        return dest;
    }

    public Vectors store(FloatBuffer buf) {
        buf.put(this.x);
        buf.put(this.y);
        return this;
    }

    public Vectors load(FloatBuffer buf) {
        this.x = buf.get();
        this.y = buf.get();
        return this;
    }

    public Vectors scale(float scale) {
        this.x *= scale;
        this.y *= scale;

        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);

        sb.append("Vector2f[");
        sb.append(this.x);
        sb.append(", ");
        sb.append(this.y);
        sb.append(']');
        return sb.toString();
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
        Vectors2f other = (Vectors2f) obj;
        if ((this.x == other.x) && (this.y == other.y)) {
            return true;
        }
        return false;
    }

}
