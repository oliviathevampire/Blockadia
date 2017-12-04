package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector4f;

public class CameraPicker {

    /**
     * get the direction of an imaginary ray casted from the camera, to the
     * direction pointed by the mouse coordinates
     *
     * @param camera : the camera
     * @param ray    : where the result ray is stored
     * @param mouseX : mouse cursor X in [0, 1]
     * @param mouseY : mouse cursor Y in [0, 1]
     */
    public static void ray(Vector3f ray, CameraProjective camera, float mouseX, float mouseY) {
        if (ray == null) {
            ray = new Vector3f();
        }

        mouseX = 2.0f * mouseX - 1.0f;
        mouseY = 2.0f * mouseY - 1.0f;

        Vector4f clipcoords = new Vector4f(mouseX, mouseY, -1.0f, 1.0f);
        Matrix4f invertedprojection = Matrix4f.invert(camera.getProjectionMatrix(), null);
        Vector4f eyecoords = Matrix4f.transform(invertedprojection, clipcoords, null);
        eyecoords.setZ(-1);
        eyecoords.setW(0);
        Matrix4f invertedview = Matrix4f.invert(camera.getViewMatrix(), null);
        Vector4f rayworld = Matrix4f.transform(invertedview, eyecoords, null);
        ray.setX(rayworld.x);
        ray.setY(rayworld.y);
        ray.setZ(rayworld.z);
    }
}
