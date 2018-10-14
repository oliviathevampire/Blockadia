package team.hdt.blockadia.engine.core_rewrite.util;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import team.hdt.blockadia.engine.core_rewrite.object.ICamera;

import java.util.HashMap;
import java.util.Map;

public class Maths {

	private static final Map<String, Matrix4f> TRANSFORMATION_MATRICES = new HashMap<String, Matrix4f>();

	private static Matrix4f viewMatrix = new Matrix4f();

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = TRANSFORMATION_MATRICES.get(translation.x + "," + translation.y + "," + scale.x + "," + scale.y);
		if (matrix == null) {
			matrix = new Matrix4f();
			matrix.identity();
			matrix.translate(translation.x, translation.y, -1);
			matrix.scale(scale.x, scale.y, 1);
			TRANSFORMATION_MATRICES.put(translation.x + "," + translation.y + "," + scale.x + "," + scale.y, matrix);
		}
		return matrix;
	}

	public static Matrix4f createTransformationMatrix(float x, float y, Vector3f rotation, float scale) {
		Matrix4f matrix = TRANSFORMATION_MATRICES.get(x + "," + y + "," + rotation.x + "," + rotation.y + "," + rotation.z + "," + scale);
		if (matrix == null) {
			matrix = new Matrix4f();
			matrix.identity();
			matrix.translate(x, y, -1);
			matrix.rotate((float)Math.toRadians(rotation.x), 1, 0, 0);
			matrix.rotate((float)Math.toRadians(rotation.y), 0, 1, 0);
			matrix.rotate((float)Math.toRadians(rotation.z), 0, 0, 1);
			matrix.scale(scale);
			TRANSFORMATION_MATRICES.put(x + "," + y + "," + rotation.x + "," + rotation.y + "," + rotation.z + "," + scale, matrix);
		}
		return matrix;
	}

	/**
	 * Creates a 3d transformation matrix.
	 * 
	 * @param position
	 *            The translation in the x axis
	 * @param rotation
	 *            The rotation
	 * @param scale
	 *            The scale
	 * @return The matrix created with all the data stored inside
	 */
	public static Matrix4f createTransformationMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
		return createTransformationMatrix(position.x, position.y, position.z, rotation.x, rotation.y, rotation.z, scale.x, scale.y, scale.z);
	}

	/**
	 * Creates a 3d transformation matrix.
	 * 
	 * @param x
	 *            The translation in the x axis
	 * @param y
	 *            The translation in the y axis
	 * @param z
	 *            The translation in the z axis
	 * @param rotationX
	 *            The rotation in the x axis
	 * @param rotationY
	 *            The rotation in the y axis
	 * @param rotationZ
	 *            The rotation in the z axis
	 * @param scaleX
	 *            The scale in the x axis
	 * @param scaleY
	 *            The scale in the y axis
	 * @param scaleZ
	 *            The scale in the z axis
	 * @return The matrix created with all the data stored inside
	 */
	public static Matrix4f createTransformationMatrix(float x, float y, float z, float rotationX, float rotationY, float rotationZ, float scaleX, float scaleY, float scaleZ) {
		Matrix4f matrix = TRANSFORMATION_MATRICES.get(x + "," + y + "," + z + "," + rotationX + "," + rotationY + "," + rotationZ + "," + scaleX + "," + scaleY + "," + scaleZ);
		if (matrix == null) {
			matrix = new Matrix4f();
			matrix.identity();
			matrix.translate(x, y, z);
			matrix.rotate((float) Math.toRadians(rotationX), 1, 0, 0);
			matrix.rotate((float) Math.toRadians(rotationY), 0, 1, 0);
			matrix.rotate((float) Math.toRadians(rotationZ), 0, 0, 1);
			matrix.scale(scaleX, scaleY, scaleZ);
			TRANSFORMATION_MATRICES.put(x + "," + y + "," + z + "," + rotationX + "," + rotationY + "," + rotationZ + "," + scaleX + "," + scaleY + "," + scaleZ, matrix);
		}
		return matrix;
	}

	/**
	 * Creates the matrix used to move the world around based on the camera position.
	 *
	 * @param camera
	 *            The camera to revolve around
	 * @param output
	 *            The output matrix that the camera info will go into
	 * @return The matrix returned or created if null was passed for the output
	 */
	public static Matrix4f createViewMatrix(ICamera camera) {
		viewMatrix.identity();
		viewMatrix.rotate((float) Math.toRadians(camera.getRotation().x), 1, 0, 0);
		viewMatrix.rotate((float) Math.toRadians(camera.getRotation().y), 0, 1, 0);
		viewMatrix.rotate((float) Math.toRadians(camera.getRotation().z), 0, 0, 1);
		viewMatrix.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
		return viewMatrix;
	}

	/**
	 * A method for clamping a variable between two values.
	 * 
	 * @param value
	 *            The value to be clamped.
	 * @param min
	 *            The lowest the value should go.
	 * @param max
	 *            The highest the value should go.
	 * @return The clamped value.
	 */
	public static double clamp(double value, double min, double max) {
		if (value < min) {
			value = min;
		}
		if (value > max) {
			value = max;
		}
		return value;
	}

	/**
	 * Interpolates between point a and b
	 * 
	 * @param a
	 *            The first position
	 * @param b
	 *            The second position
	 * @param blend
	 *            The amount to interpolate
	 * @return The interpolated value
	 */
	public static float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}
}