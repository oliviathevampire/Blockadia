package team.hdt.blockadia.game_engine_old.common;

import team.hdt.blockadia.game_engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;

/**
 * This interface is used throughout the engine wherever the camera is involved,
 * so that the engine doesn't rely at all on the camera's implementation.
 *
 * @author Karl
 */
public interface CameraInterface {

    public Vectors3f getPosition();

    /**
     * @return The distance of the near pane of the view frustum.
     */
    public float getNearPlane();

    /**
     * @return The distance of the view frustum's far plane.
     */
    public float getFarPlane();

    /**
     * @return The field of view angle for the view frustum.
     */
    public float getFOV();

    /**
     * @return The view matrix created by the current camera position and
     * rotation.
     */
    public Matrix4fs getViewMatrix();


    /**
     * Prepares the camera for the reflection render pass.
     */
    public void reflect(float waterHeight);

    /**
     * Checks inputs and carries out smooth camera movement. Should be called
     * every frame.
     */
    public void moveCamera();

    /**
     * @return The camera's pitch (x rotation).
     */
    public float getPitch();

    /**
     * @return The camera's yaw (y rotation).
     */
    public float getYaw();

    /**
     * @return The distance of the in-focus plane from the camera.
     */
    public float getAimDistance();

}
