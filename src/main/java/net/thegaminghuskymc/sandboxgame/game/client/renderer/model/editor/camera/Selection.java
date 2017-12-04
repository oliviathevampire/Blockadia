package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;


import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.Positioneable;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.Sizeable;

public abstract class Selection implements Positioneable, Sizeable {

    protected final CameraTool cameraController;

    public Selection(CameraTool cameraController) {
        this.cameraController = cameraController;
    }

    public void update() {

    }

    public abstract int getX();

    public abstract int getY();

    public abstract int getZ();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getDepth();

    public abstract Vector3i getFace();

    @Override
    public float getPositionX() {
        return (this.getX() * this.cameraController.getBlockSizeUnit());
    }

    @Override
    public void setPositionX(float x) {
    }

    @Override
    public float getPositionY() {
        return ((this.getY() + 0.05f) * this.cameraController.getBlockSizeUnit());
    }

    @Override
    public void setPositionY(float y) {
    }

    @Override
    public float getPositionZ() {
        return (this.getZ() * this.cameraController.getBlockSizeUnit());
    }

    @Override
    public void setPositionZ(float z) {
    }

    @Override
    public float getPositionVelocityX() {
        return 0;
    }

    @Override
    public void setPositionVelocityX(float vx) {
    }

    @Override
    public float getPositionVelocityY() {
        return 0;
    }

    @Override
    public void setPositionVelocityY(float vy) {
    }

    @Override
    public float getPositionVelocityZ() {
        return 0;
    }

    @Override
    public void setPositionVelocityZ(float vz) {
    }

    @Override
    public float getPositionAccelerationX() {
        return 0;
    }

    @Override
    public void setPositionAccelerationX(float ax) {
    }

    @Override
    public float getPositionAccelerationY() {
        return 0;
    }

    @Override
    public void setPositionAccelerationY(float ay) {
    }

    @Override
    public float getPositionAccelerationZ() {
        return 0;
    }

    @Override
    public void setPositionAccelerationZ(float az) {
    }

    @Override
    public float getSizeX() {
        return (this.getWidth() * this.cameraController.getBlockSizeUnit());
    }

    @Override
    public void setSizeX(float x) {
    }

    @Override
    public float getSizeY() {
        return (this.getHeight() * this.cameraController.getBlockSizeUnit());
    }

    @Override
    public void setSizeY(float y) {
    }

    @Override
    public float getSizeZ() {
        return (this.getDepth() * this.cameraController.getBlockSizeUnit());
    }

    @Override
    public void setSizeZ(float z) {
    }

    @Override
    public float getSizeVelocityX() {
        return 0;
    }

    @Override
    public void setSizeVelocityX(float vx) {
    }

    @Override
    public float getSizeVelocityY() {
        return 0;
    }

    @Override
    public void setSizeVelocityY(float vy) {
    }

    @Override
    public float getSizeVelocityZ() {
        return 0;
    }

    @Override
    public void setSizeVelocityZ(float vz) {
    }

    @Override
    public float getSizeAccelerationX() {
        return 0;
    }

    @Override
    public void setSizeAccelerationX(float ax) {
    }

    @Override
    public float getSizeAccelerationY() {
        return 0;
    }

    @Override
    public void setSizeAccelerationY(float ay) {
    }

    @Override
    public float getSizeAccelerationZ() {
        return 0;
    }

    @Override
    public void setSizeAccelerationZ(float az) {
    }
}
