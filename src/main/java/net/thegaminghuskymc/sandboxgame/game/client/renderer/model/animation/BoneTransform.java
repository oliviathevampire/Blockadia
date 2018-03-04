package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation;


import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Quaternion;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;

/**
 * 
 * Represents the local bone-space transform of a joint at a certain keyframe
 * during an animation. This includes the position and rotation of the joint,
 * relative to the parent joint (for the root joint it's relative to the model's
 * origin, seeing as the root joint has no parent). The transform is stored as a
 * position vector and a quaternion (rotation) so that these values can be
 * easily interpolated, a functionality that this class also provides.
 * 
 * @author Karl
 *
 */

public class BoneTransform {

	// remember, this position and rotation are relative to the parent bone!
	private final Vector3f position;
	private final Quaternion rotation;
	private final Matrix4f localTransform;

	/**
	 * 
	 * @param position
	 *            - the position of the joint relative to the parent joint
	 *            (bone-space) at a certain keyframe. For example, if this joint is
	 *            at (5, 12, 0) in the model's coordinate system, and the parent of
	 *            this joint is at (2, 8, 0), then the position of this joint
	 *            relative to the parent is (3, 4, 0).
	 * @param rotation
	 *            - the rotation of the joint relative to the parent joint
	 *            (bone-space) at a certain keyframe.
	 */
	public BoneTransform(Vector3f position, Quaternion rotation) {
		this();
		this.set(position, rotation);
	}

	public BoneTransform() {
		this.position = new Vector3f();
		this.rotation = new Quaternion();
		this.localTransform = new Matrix4f();
	}

	public void set(Vector3f position, Quaternion rotation) {
		this.position.set(position);
		this.rotation.set(rotation);
		this.localTransform.setIdentity();
		this.localTransform.translate(this.position);
		Matrix4f.mul(this.localTransform, this.rotation.toRotationMatrix(), this.localTransform);
	}

	/**
	 * In this method the bone-space transform matrix is constructed by translating
	 * an identity matrix using the position variable and then applying the
	 * rotation. The rotation is applied by first converting the quaternion into a
	 * rotation matrix, which is then multiplied with the transform matrix.
	 * 
	 * @return This bone-space joint transform as a matrix. The exact same transform
	 *         as represented by the position and rotation in this instance, just in
	 *         matrix form.
	 */
	public Matrix4f getLocalTransform() {
		return (this.localTransform);
	}

	/**
	 * Interpolates between two transforms based on the progression value. The
	 * result is a new transform which is part way between the two original
	 * transforms. The translation can simply be linearly interpolated, but the
	 * rotation interpolation is slightly more complex, using a method called
	 * "SLERP" to spherically-linearly interpolate between 2 quaternions
	 * (rotations). This gives a much much better result than trying to linearly
	 * interpolate between Euler rotations.
	 * 
	 * @param frameA
	 *            - the previous transform
	 * @param frameB
	 *            - the next transform
	 * @param progression
	 *            - a number between 0 and 1 indicating how far between the two
	 *            transforms to interpolate. A progression value of 0 would return a
	 *            transform equal to "frameA", a value of 1 would return a transform
	 *            equal to "frameB". Everything else gives a transform somewhere
	 *            in-between the two.
	 * @return
	 */
	public static BoneTransform interpolate(BoneTransform frameA, BoneTransform frameB, float progression) {
		Vector3f pos = interpolate(frameA.position, frameB.position, progression);
		Quaternion rot = Quaternion.interpolate(frameA.rotation, frameB.rotation, progression);
		return new BoneTransform(pos, rot);
	}

	/**
	 * Linearly interpolates between two translations based on a "progression"
	 * value.
	 * 
	 * @param start
	 *            - the start translation.
	 * @param end
	 *            - the end translation.
	 * @param progression
	 *            - a value between 0 and 1 indicating how far to interpolate
	 *            between the two translations.
	 * @return
	 */
	private static Vector3f interpolate(Vector3f start, Vector3f end, float progression) {
		float x = start.x + (end.x - start.x) * progression;
		float y = start.y + (end.y - start.y) * progression;
		float z = start.z + (end.z - start.z) * progression;
		return new Vector3f(x, y, z);
	}

	public final Vector3f getTranslation() {
		return (this.position);
	}

	public final Quaternion getRotation() {
		return (this.rotation);
	}
}
