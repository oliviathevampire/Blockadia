package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.control.Control;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

import static org.lwjgl.glfw.GLFW.*;

/**
 * a camera which follow the given entity, 3rd perso view
 */
public class CameraPerspectiveWorldEntity extends CameraPerspectiveWorldCentered {

    /**
     * the entity which this camera follows
     */
    private Entity entity;

    public CameraPerspectiveWorldEntity(GLFWWindow window) {
        super(window);
    }

    @Override
    public void update() {
        super.update();
        float x = this.getEntity().getPositionX() + this.getEntity().getSizeX() * 0.5f;
        float y = this.getEntity().getPositionY() + this.getEntity().getSizeY() * 0.5f;
        float z = this.getEntity().getPositionZ() + this.getEntity().getSizeZ() * 0.5f;
        super.setCenter(x, y, z);

        if (GLH.glhGetWindow().isKeyPressed(GLFW_KEY_SPACE)) {
            this.getEntity().jump();
        }

        if (GLH.glhGetWindow().isKeyPressed(GLFW_KEY_W)) {
            this.getEntity().addControl(Control.FORWARD);
        }
        if (GLH.glhGetWindow().isKeyPressed(GLFW_KEY_S)) {
            this.getEntity().addControl(Control.BACKWARD);
        }
        if (GLH.glhGetWindow().isKeyPressed(GLFW_KEY_D)) {
            this.getEntity().addControl(Control.STRAFE_RIGHT);
        }
        if (GLH.glhGetWindow().isKeyPressed(GLFW_KEY_A)) {
            this.getEntity().addControl(Control.STRAFE_LEFT);
        }

    }

    public Entity getEntity() {
        return (this.entity);
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
        super.setWorld(entity.getWorld());
    }

    @Override
    public void invokeCursorPos(GLFWWindow window, double xpos, double ypos) {
        double dy = -(window.getMouseDX() * 0.2f);
        this.entity.setRotationY(this.entity.getRotationY() + (float) dy);
        super.increasePitch((float) (window.getMouseDY() * 0.1f));
        super.increaseAngleAroundCenter((float) dy);
    }

    @Override
    public void invokeKeyRelease(GLFWWindow glfwWindow, int key, int scancode, int mods) {

    }

    @Override
    public void invokeKeyPress(GLFWWindow glfwWindow, int key, int scancode, int mods) {
        if (glfwWindow.isKeyPressed(GLFW_KEY_P)) {
            Vector3f pos = new Vector3f();
            pos.set(this.entity.getPositionX() + 1, this.entity.getPositionY(), this.entity.getPositionZ());
            pos.add(this.entity.getViewVector());
            pos.add(this.entity.getViewVector());
            super.setBlock(Blocks.COBBLESTONE, pos);
        }
        if (glfwWindow.isKeyPressed(GLFW_KEY_R)) {
            Vector3f pos = new Vector3f();
            pos.set(this.entity.getPositionX(), this.entity.getPositionY(), this.entity.getPositionZ());
            pos.add(this.entity.getViewVector());
            pos.add(this.entity.getViewVector());
            super.setBlock(Blocks.AIR, pos);
        }
    }

    @Override
    public void invokeMouseScroll(GLFWWindow window, double xpos, double ypos) {
        float speed = Maths.max(super.getDistanceFromCenter() * 0.1f, 0.2f);
        this.increaseDistanceFromCenter((float) (-ypos * speed));
    }

    @Override
    public void setYaw(float yaw) {
        super.setYaw(yaw + this.getEntity().getRotationY());
    }
}
