package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.*;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.Positioneable;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.Sizeable;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraPicker;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.Raycasting;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.RaycastingCallback;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventMouseScroll;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiModelView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiWindowRigging;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

public class CameraToolRigging extends CameraTool implements Positioneable, Sizeable {

    protected final Vector3i hovered;
    private final Vector3i firstBlock;
    private final Vector3i secondBlock;
    private Vector3i face;
    private int expansion;

    public CameraToolRigging(GuiModelView guiModelView) {
        super(guiModelView);
        this.hovered = new Vector3i();
        this.firstBlock = new Vector3i();
        this.secondBlock = new Vector3i();
        this.expansion = 0;
    }

    @Override
    public boolean action(ModelInstance modelInstance, EditableModelLayer editableModelLayer) {
        GuiWindowRigging gui = new GuiWindowRigging(this);
        gui.open(this.guiModelView);
        return (false);
    }

    private void expand(int d) {
        this.expansion += d;
        this.updateSecondBlock();
    }

    @Override
    public void onMouseMove() {
    }

    @Override
    public void onRightPressed() {
        ModelEditorCamera camera = this.getCamera();
        camera.getWindow().setCursor(false);
        float u = this.getBlockSizeUnit();
        float x = 0;
        float y = 0;
        float z = 0;
        this.getCamera().setCenter((x + 0.5f) * u, (y + 0.5f) * u, (z + 0.5f) * u);
        camera.setDistanceFromCenter((float) Vector3f.distance(camera.getCenter(), camera.getPosition()));
    }

    @Override
    public void onRightReleased() {
        this.getCamera().getWindow().setCursor(true);
        this.getCamera().getWindow().setCursorCenter();
    }

    @Override
    public void onLeftPressed() {
        this.expansion = 0;
    }

    @Override
    public void onLeftReleased() {
        this.expansion = 0;
    }

    private final void updateHoveredBlock() {

        ModelInstance modelInstance = this.guiModelView.getSelectedModelInstance();
        EditableModelLayer modelLayer = this.guiModelView.getSelectedModelLayer();

        // extract objects
        if (modelInstance == null || modelLayer == null) {
            return;
        }
        Entity entity = modelInstance.getEntity();
        float s = modelLayer.getBlockSizeUnit();
        ModelEditorCamera camera = (ModelEditorCamera) this.getCamera();

        // origin relatively to the model
        Vector4f origin = new Vector4f(camera.getPosition(), 1.0f);
        Matrix4f transform = new Matrix4f();
        transform.translate(-entity.getPositionX(), -entity.getPositionY(), -entity.getPositionZ());
        transform.scale(1 / s);
        Matrix4f.transform(transform, origin, origin);

        // ray relatively to the model
        Vector3f ray = new Vector3f();
        CameraPicker.ray(ray, camera, this.guiModelView.getMouseX(), this.guiModelView.getMouseY());

        Vector3i pos = new Vector3i();
        Raycasting.raycast(origin.x, origin.y, origin.z, ray.x, ray.y, ray.z, 256.0f, 256.0f, 256.0f,
                new RaycastingCallback() {
                    @Override
                    public boolean onRaycastCoordinates(int x, int y, int z, Vector3i theFace) {
                        // System.out.println(x + " : " + y + " : " + z);
                        if (y < 0 || modelLayer.getBlockData(pos.set(x, y, z)) != null) {
                            hovered.set(x, y, z);
                            face = theFace;
                            return (true);
                        }
                        return (false);
                    }
                });
    }

    @Override
    public void onMouseScroll(GuiEventMouseScroll<GuiModelView> event) {
        if (super.guiModelView.isLeftPressed()) {
            int dy = -Maths.sign(event.getScrollY());
            this.expand(dy);
        } else {
            float speed = this.getCamera().getDistanceFromCenter() * 0.14f;
            this.getCamera().increaseDistanceFromCenter((float) (-event.getScrollY() * speed));
        }
    }

    private final void updateSecondBlock() {
        if (this.face == null) {
            this.secondBlock.set(this.hovered);
            return;
        }
        int x = this.hovered.x + this.expansion * this.face.x;
        int y = this.hovered.y + this.expansion * this.face.y;
        int z = this.hovered.z + this.expansion * this.face.z;
        this.secondBlock.set(x, y, z);
    }

    @Override
    public void onUpdate() {
        this.updateCameraRotation();
        this.updateSelection();
    }

    private final void updateSelection() {
        if (!this.guiModelView.isLeftPressed()) {
            this.firstBlock.set(this.hovered);
        }
        this.updateSecondBlock();
        super.guiModelView.getWorldRenderer().getLineRendererFactory().addBox(this, this, Color.GREEN);
    }

    private final void updateCameraRotation() {
        // rotate
        if (this.guiModelView.isRightPressed()) {
            float pitch = (float) ((this.guiModelView.getPrevMouseY() - this.guiModelView.getMouseY()) * 64.0f);
            this.getCamera().increasePitch(pitch);

            float angle = (float) ((this.guiModelView.getPrevMouseX() - this.guiModelView.getMouseX()) * 128.0f);
            this.getCamera().increaseAngleAroundCenter(angle);

            this.hovered.set(0, 0, 0);
        } else {
            this.updateHoveredBlock();
        }

        float u = this.getBlockSizeUnit();
        float x = 0;
        float y = 0;
        float z = 0;
        this.getCamera().setCenter((x + 0.5f) * u, (y + 0.5f) * u, (z + 0.5f) * u);
    }

    @Override
    public String getName() {
        return ("Remove");
    }

    public Vector3i getFirstBlock() {
        return (this.firstBlock);
    }

    public Vector3i getSecondBlock() {
        return (this.secondBlock);
    }

    public Vector3i getFace() {
        return (this.face);
    }

    public final int getX() {
        return (Maths.min(this.firstBlock.x, this.secondBlock.x));
    }

    public final int getY() {
        return (Maths.min(this.firstBlock.y, this.secondBlock.y));
    }

    public final int getZ() {
        return (Maths.min(this.firstBlock.z, this.secondBlock.z));
    }

    public final int getWidth() {
        return (Maths.abs(this.firstBlock.x - this.secondBlock.x) + 1);
    }

    public final int getHeight() {
        return (Maths.abs(this.firstBlock.y - this.secondBlock.y) + 1);
    }

    public final int getDepth() {
        return (Maths.abs(this.firstBlock.z - this.secondBlock.z) + 1);
    }

    @Override
    public float getPositionX() {
        return (this.getX() * this.getBlockSizeUnit());
    }

    @Override
    public void setPositionX(float x) {
    }

    @Override
    public float getPositionY() {
        return ((this.getY() + 0.05f) * this.getBlockSizeUnit());
    }

    @Override
    public void setPositionY(float y) {
    }

    @Override
    public float getPositionZ() {
        return (this.getZ() * this.getBlockSizeUnit());
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
        return (this.getWidth() * this.getBlockSizeUnit());
    }

    @Override
    public void setSizeX(float x) {
    }

    @Override
    public float getSizeY() {
        return (this.getHeight() * this.getBlockSizeUnit());
    }

    @Override
    public void setSizeY(float y) {
    }

    @Override
    public float getSizeZ() {
        return (this.getDepth() * this.getBlockSizeUnit());
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
