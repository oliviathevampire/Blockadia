package net.thegaminghuskymc.sandboxgame.engine.util.math;

/**
 * A quaternion simply represents a 3D rotation. The maths behind it is quite
 * complex (literally; it involves complex numbers) so I wont go into it in too
 * much detail. The important things to note are that it represents a 3d
 * rotation, it's very easy to interpolate between two quaternion rotations
 * (which would not be easy to do correctly with Euler rotations or rotation
 * matrices), and you can convert to and from matrices fairly easily. So when we
 * need to interpolate between rotations we'll represent them as quaternions,
 * but when we need to apply the rotations to anything we'll convert back to a
 * matrix.
 * 
 * An quick introduction video to quaternions:
 * https://www.youtube.com/watch?v=SCbpxiCN0U0
 * 
 * and a slightly longer one: https://www.youtube.com/watch?v=fKIss4EV6ME&t=0s
 * 
 * 
 * @author Karl
 *
 */
public class Quaternion {

	private float x, y, z, w;

	public Quaternion() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
		this.w = 1.0f;
	}

	public Quaternion(float x, float y, float z, float w) {
		this.set(x, y, z, w);
	}

	/**
	 * Normalizes the quaternion.
	 */
	public void normalize() {
		float mag = (float) Math.sqrt(w * w + x * x + y * y + z * z);
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
	}

	/**
	 * convert euler angles to quaternion
	 */
	public static final Quaternion toQuaternion(Vector3f rot) {
		return (toQuaternion(rot.x, rot.y, rot.z));
	}

	public static final Quaternion toQuaternion(Quaternion dst, Vector3f rot) {
		return (toQuaternion(dst, rot.x, rot.y, rot.z));
	}

	/**
	 * convert euler angles to quaternion
	 * 
	 * @param pitch
	 * @param yaw
	 * @param roll
	 * @return
	 */
	public static final Quaternion toQuaternion(Quaternion dst, double pitch, double yaw, double roll) {
		if (dst == null) {
			dst = new Quaternion();
		}
		dst.set(pitch, yaw, roll);
		return (dst);
	}

	/**
	 * convert euler angles to quaternion
	 * 
	 * @param rx
	 * @param ry
	 * @param rz
	 * @return
	 */
	public static final Quaternion toQuaternion(double rx, double ry, double rz) {
		return (toQuaternion(null, rx, ry, rz));
	}

	/**
	 * @return : euler angles of the given quaternion
	 */
	public static final Vector3f toEulerAngle(Quaternion quaternion) {
		return (toEulerAngle(quaternion.x, quaternion.y, quaternion.z, quaternion.w));
	}

	/**
	 * @return : euler angles of the given quaternion
	 */
	public static final Vector3f toEulerAngle(float x, float y, float z, float w) {
		return (toEulerAngle(null, x, y, z, w));
	}

	public static final Vector3f toEulerAngle(Vector3f rotate, float x, float y, float z, float w) {
		if (rotate == null) {
			rotate = new Vector3f();
		}
		rotate.x = toEulerAngleX(x, y, z, w);
		rotate.y = toEulerAngleY(x, y, z, w);
		rotate.z = toEulerAngleZ(x, y, z, w);

		return (rotate);
	}

	public static final float toEulerAngleX(Quaternion q) {
		return (toEulerAngleX(q.x, q.y, q.z, q.w));
	}

	public static final float toEulerAngleY(Quaternion q) {
		return (toEulerAngleY(q.x, q.y, q.z, q.w));
	}

	public static final float toEulerAngleZ(Quaternion q) {
		return (toEulerAngleZ(q.x, q.y, q.z, q.w));
	}

	public static final float toEulerAngleX(float x, float y, float z, float w) {
		double sinp = +2.0 * (w * y - z * x);
		return ((float) (Math.abs(sinp) >= 1 ? Math.PI / 2.0f : Math.asin(sinp)));
	}

	public static final float toEulerAngleY(float x, float y, float z, float w) {
		double sinr = +2.0 * (w * x + y * z);
		double cosr = +1.0 - 2.0 * (x * x + y * y);
		return ((float) Math.atan2(sinr, cosr));
	}

	public static final float toEulerAngleZ(float x, float y, float z, float w) {
		double siny = +2.0 * (w * z + x * y);
		double cosy = +1.0 - 2.0 * (y * y + z * z);
		return ((float) Math.atan2(siny, cosy));
	}

	/**
	 * Converts the quaternion to a 4x4 matrix representing the exact same rotation
	 * as this quaternion. (The rotation is only contained in the top-left 3x3 part,
	 * but a 4x4 matrix is returned here for convenience seeing as it will be
	 * multiplied with other 4x4 matrices).
	 * 
	 * More detailed explanation here:
	 * http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/
	 * 
	 * @return The rotation matrix which represents the exact same rotation as this
	 *         quaternion.
	 */
	public Matrix4f toRotationMatrix() {
		return toRotationMatrix(this.x, this.y, this.z, this.w);
	}

	public static final Matrix4f toRotationMatrix(float x, float y, float z, float w) {
		Matrix4f matrix = new Matrix4f();
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yz = y * z;
		final float yw = y * w;
		final float zw = z * w;
		final float xSquared = x * x;
		final float ySquared = y * y;
		final float zSquared = z * z;
		matrix.m00 = 1 - 2 * (ySquared + zSquared);
		matrix.m01 = 2 * (xy - zw);
		matrix.m02 = 2 * (xz + yw);
		matrix.m03 = 0;
		matrix.m10 = 2 * (xy + zw);
		matrix.m11 = 1 - 2 * (xSquared + zSquared);
		matrix.m12 = 2 * (yz - xw);
		matrix.m13 = 0;
		matrix.m20 = 2 * (xz - yw);
		matrix.m21 = 2 * (yz + xw);
		matrix.m22 = 1 - 2 * (xSquared + ySquared);
		matrix.m23 = 0;
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		matrix.m33 = 1;
		return matrix;
	}

	/**
	 * Extracts the rotation part of a transformation matrix and converts it to a
	 * quaternion using the magic of maths.
	 * 
	 * More detailed explanation here:
	 * http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToQuaternion/index.htm
	 * 
	 * @param matrix
	 *            - the transformation matrix containing the rotation which this
	 *            quaternion shall represent.
	 */
	public static Quaternion fromMatrix(Matrix4f matrix) {
		float w, x, y, z;
		float diagonal = matrix.m00 + matrix.m11 + matrix.m22;
		if (diagonal > 0) {
			float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);
			w = w4 / 4f;
			x = (matrix.m21 - matrix.m12) / w4;
			y = (matrix.m02 - matrix.m20) / w4;
			z = (matrix.m10 - matrix.m01) / w4;
		} else if ((matrix.m00 > matrix.m11) && (matrix.m00 > matrix.m22)) {
			float x4 = (float) (Math.sqrt(1f + matrix.m00 - matrix.m11 - matrix.m22) * 2f);
			w = (matrix.m21 - matrix.m12) / x4;
			x = x4 / 4f;
			y = (matrix.m01 + matrix.m10) / x4;
			z = (matrix.m02 + matrix.m20) / x4;
		} else if (matrix.m11 > matrix.m22) {
			float y4 = (float) (Math.sqrt(1f + matrix.m11 - matrix.m00 - matrix.m22) * 2f);
			w = (matrix.m02 - matrix.m20) / y4;
			x = (matrix.m01 + matrix.m10) / y4;
			y = y4 / 4f;
			z = (matrix.m12 + matrix.m21) / y4;
		} else {
			float z4 = (float) (Math.sqrt(1f + matrix.m22 - matrix.m00 - matrix.m11) * 2f);
			w = (matrix.m10 - matrix.m01) / z4;
			x = (matrix.m02 + matrix.m20) / z4;
			y = (matrix.m12 + matrix.m21) / z4;
			z = z4 / 4f;
		}
		return new Quaternion(x, y, z, w);
	}

	/**
	 * Interpolates between two quaternion rotations and returns the resulting
	 * quaternion rotation. The interpolation method here is "nlerp", or
	 * "normalized-lerp". Another mnethod that could be used is "slerp", and you can
	 * see a comparison of the methods here:
	 * https://keithmaggio.wordpress.com/2011/02/15/math-magician-lerp-slerp-and-nlerp/
	 * 
	 * and here:
	 * http://number-none.com/product/Understanding%20Slerp,%20Then%20Not%20Using%20It/
	 * 
	 * @param a
	 * @param b
	 * @param blend
	 *            - a value between 0 and 1 indicating how far to interpolate
	 *            between the two quaternions.
	 * @return The resulting interpolated rotation in quaternion format.
	 */
	public static Quaternion interpolate(Quaternion a, Quaternion b, float blend) {
		Quaternion result = new Quaternion(0, 0, 0, 1);
		float dot = a.w * b.w + a.x * b.x + a.y * b.y + a.z * b.z;
		float blendI = 1f - blend;
		if (dot < 0) {
			result.w = blendI * a.w + blend * -b.w;
			result.x = blendI * a.x + blend * -b.x;
			result.y = blendI * a.y + blend * -b.y;
			result.z = blendI * a.z + blend * -b.z;
		} else {
			result.w = blendI * a.w + blend * b.w;
			result.x = blendI * a.x + blend * b.x;
			result.y = blendI * a.y + blend * b.y;
			result.z = blendI * a.z + blend * b.z;
		}
		result.normalize();
		return result;
	}

	public void set(Quaternion quaternion) {
		this.set(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
	}

	/** setter function using euler angles */
	public void set(float rx, float ry, float rz) {
		// Abbreviations for the various angular functions
		double cp = Math.cos(rx * 0.5);
		double sp = Math.sin(rx * 0.5);
		double cr = Math.cos(ry * 0.5);
		double sr = Math.sin(ry * 0.5);
		double cy = Math.cos(rz * 0.5);
		double sy = Math.sin(rz * 0.5);

		this.w = (float) (cy * cr * cp + sy * sr * sp);
		this.x = (float) (cy * sr * cp - sy * cr * sp);
		this.y = (float) (cy * cr * sp + sy * sr * cp);
		this.z = (float) (sy * cr * cp - cy * sr * sp);
	}

	/** setter function using euler angles */
	public void set(double rx, double ry, double rz) {
		this.set((float) rx, (float) ry, (float) rz);
	}

	/** setter function using quaternions coordinates */
	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		this.normalize();
	}

	public final float getX() {
		return (this.x);
	}

	public final float getY() {
		return (this.y);
	}

	public final float getZ() {
		return (this.z);
	}

	public final float getW() {
		return (this.w);
	}

}
