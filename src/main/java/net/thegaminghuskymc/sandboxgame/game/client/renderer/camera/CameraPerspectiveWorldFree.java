package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import org.lwjgl.glfw.GLFW;

public class CameraPerspectiveWorldFree extends CameraPerspectiveWorld {

    private static final int STATE_MOVE_FORWARD = 1;
    private static final int STATE_MOVE_BACKWARD = 2;
    private static final int STATE_MOVE_LEFT = 4;
    private static final int STATE_MOVE_RIGHT = 8;
    private double _prevx = 200;
    private double _prevy = 200;

    public CameraPerspectiveWorldFree(GLFWWindow window) {
        super(window);
    }

    @Override
    public void update() {
        super.update();
        this.setSpeed(0.1f);
        this.updateMove();
    }

    private void updateMove() {
        Vector3f vel = this.getPositionVelocity();
        if (this.hasState(STATE_MOVE_FORWARD)) {
            vel.setX(this.getViewVector().x);
            vel.setY(this.getViewVector().y);
            vel.setZ(this.getViewVector().z);
        } else if (this.hasState(STATE_MOVE_BACKWARD)) {
            vel.setX(-this.getViewVector().x);
            vel.setY(-this.getViewVector().y);
            vel.setZ(-this.getViewVector().z);
        } else if (this.hasState(STATE_MOVE_RIGHT)) {
            vel.setX(-this.getViewVector().z);
            vel.setY(0);
            vel.setZ(this.getViewVector().x);
        } else if (this.hasState(STATE_MOVE_LEFT)) {
            vel.setX(this.getViewVector().z);
            vel.setY(0);
            vel.setZ(-this.getViewVector().x);
        } else {
            vel.setX(0);
            vel.setY(0);
            vel.setZ(0);
        }

        this.move(vel.scale(1.0F));
    }

    @Override
    public void invokeKeyRelease(GLFWWindow window, int key, int scancode, int mods) {
        if (key == GLFW.GLFW_KEY_W) {
            this.unsetState(STATE_MOVE_FORWARD);
        }
        if (key == GLFW.GLFW_KEY_S) {
            this.unsetState(STATE_MOVE_BACKWARD);
        }
        if (key == GLFW.GLFW_KEY_A) {
            this.unsetState(STATE_MOVE_LEFT);
        }
        if (key == GLFW.GLFW_KEY_D) {
            this.unsetState(STATE_MOVE_RIGHT);
        }
    }

    @Override
    public void invokeKeyPress(GLFWWindow window, int key, int scancode, int mods) {
        if (key == GLFW.GLFW_KEY_W) {
            this.setState(STATE_MOVE_FORWARD);
        }
        if (key == GLFW.GLFW_KEY_A) {
            this.setState(STATE_MOVE_LEFT);
        }
        if (key == GLFW.GLFW_KEY_D) {
            this.setState(STATE_MOVE_RIGHT);
        }
        if (key == GLFW.GLFW_KEY_S) {
            this.setState(STATE_MOVE_BACKWARD);
        }
    }

    @Override
    public void invokeCursorPos(GLFWWindow window, double xpos, double ypos) {
        this.increaseYaw((float) ((xpos - _prevx) * 0.3f));
        this.increasePitch((float) ((ypos - _prevy) * 0.3f));

        _prevx = xpos;
        _prevy = ypos;
    }
}
