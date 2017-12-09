package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;

public class CameraOrthographicWorld extends CameraProjectiveWorld {

    private float left, right;
    private float bot, top;
    private float near, far;

    public CameraOrthographicWorld(GLFWWindow window) {
        super(window);
        this.left = -1;
        this.right = 1;
        this.bot = -1;
        this.top = 1;
        this.near = 0.01f;
        this.far = 1000.0f;
    }

    @Override
    protected void createProjectionMatrix(Matrix4f dst) {
        Matrix4f.orthographic(dst, this.left * this.getAspect(), this.right * this.getAspect(),
                this.bot / this.getAspect(), this.top / this.getAspect(), this.near, this.far);
    }

    public float getRight() {
        return (this.right);
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getLeft() {
        return (this.left);
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return (this.top);
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getBot() {
        return (this.bot);
    }

    public void setBot(float bot) {
        this.bot = bot;
    }

    public float getFar() {
        return (this.far);
    }

    public void setFar(float far) {
        this.far = far;
    }

    @Override
    public boolean isPointInFrustum(float x, float y, float z) {
        return true;
    }

    @Override
    public boolean isBoxInFrustum(float x, float y, float z, float sizex, float sizey, float sizez) {
        return true;
    }

    @Override
    public boolean isSphereInFrustum(Vector3f center, float radius) {
        return true;
    }

    @Override
    public Camera clone() {
        CameraOrthographicWorld cam = new CameraOrthographicWorld(this.getWindow());
        return (cam);
    }

}