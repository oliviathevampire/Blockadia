package net.thegaminghuskymc.sandboxgame.engine.util.math;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.Serializable;


public class Vector implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 7476611988104877349L;
	
	public double x, y, z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector() {
		this(0,0,0);
	}
	
	public Vector(double i) {
		this(i,i,i);
	}
	
	public Vector(Vector v) {
		this(v.x, v.y, v.z);
	}
	
	public Vector(Vector3f v) {
		this(v.x, v.y, v.z);
	}
	
	public Vector(Vector4f v) {
		this(v.x, v.y, v.z);
	}
	
	public Vector(BlockPosOld v) {
		this(v.x, v.y, v.z);
	}
	
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getBlockX() {
		return (int) Math.round(x);
	}
	
	public int getBlockY() {
		return (int) Math.round(y);
	}
	
	public int getBlockZ() {
		return (int) Math.round(z);
	}
	
	public void normalise() {
		double l = length();
		x /= l;
		y /= l;
		z /= l;
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared() {
		return x * x + y * y + z * z;
	}
	
	public Vector negate(Vector target) {
		if (target != null) {
			target.x = -x;
			target.y = -y;
			target.z = -z;
			return target;
		} else {
			return new Vector(-x, -y, -z);
		}
	}
	
	public Vector negate() {
		return negate(this);
	}
	
	@Override
	public Vector clone() {
		return new Vector(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			Vector v = (Vector) o;
			return (v.x == x && v.y == y && v.z == z);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append(getClass().getSimpleName())
				.append("[")
				.append(x)
				.append(", ")
				.append(y)
				.append(", ")
				.append(z)
				.append("]")
				.toString();
	}

	public Vector3f toVector3f() {
		return new Vector3f((float) x, (float) y, (float) z);
	}
	
	public static Vector add(Vector left, Vector right, Vector target) {
		if (target == null) {
			target = new Vector(left.x + right.x, left.y + right.y, left.z + right.z);
		} else {
			target.set(left.x + right.x, left.y + right.y, left.z + right.z);
		}
		return target;
	}

	public static Vector mul(Vector left, Vector right, Vector target) {
		if (target == null) {
			target = new Vector(left.x * right.x, left.y * right.y, left.z * right.z);
		} else {
			target.set(left.x + right.x, left.y + right.y, left.z + right.z);
		}
		return target;
	}

	public static Vector mul(Vector left, double right, Vector target) {
		if (target == null) {
			target = new Vector(left.x * right, left.y * right, left.z * right);
		} else {
			target.set(left.x * right, left.y * right, left.z * right);
		}
		return target;
	}

	public Vector add(Vector value) {
		return add(this, value, this);
	}
}
