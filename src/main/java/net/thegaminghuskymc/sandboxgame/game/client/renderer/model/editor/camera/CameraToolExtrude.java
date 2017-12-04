package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;

import net.thegaminghuskymc.sandboxgame.engine.faces.Face;
import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.*;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.Positioneable;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.Sizeable;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks.BlockRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraPicker;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.Raycasting;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventMouseScroll;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.lines.Line;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.lines.LineRendererFactory;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiModelView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

public class CameraToolExtrude extends CameraTool implements Positioneable, Sizeable {

    protected final Vector3i hovered;
    private final Vector3i firstBlock;
    private final Vector3i secondBlock;
    private Face face;
    private Vector3f[] quad;
    private Line[] lines;

    public CameraToolExtrude(GuiModelView guiModelView) {
        super(guiModelView);
        this.hovered = new Vector3i();
        this.firstBlock = new Vector3i();
        this.secondBlock = new Vector3i();
        this.quad = new Vector3f[]{new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f()};
        this.lines = new Line[]{new Line(this.quad[0], Color.YELLOW, this.quad[1], Color.YELLOW),
                new Line(this.quad[1], Color.YELLOW, this.quad[2], Color.YELLOW),
                new Line(this.quad[2], Color.YELLOW, this.quad[3], Color.YELLOW),
                new Line(this.quad[3], Color.YELLOW, this.quad[0], Color.YELLOW)};
    }

    @Override
    public boolean action(ModelInstance modelInstance, EditableModelLayer modelLayer) {

        boolean updated = false;

        int stepx = -face.getVector().x;
        int stepy = -face.getVector().y;
        int stepz = -face.getVector().z;

        int x1 = 0;
        if (stepx < 0) {
            x1 = modelLayer.getMinx() - 1;
        } else if (stepx > 0) {
            x1 = modelLayer.getMaxx() + 1;
        }

        int y1 = 0;
        if (stepy < 0) {
            y1 = modelLayer.getMiny() - 1;
        } else if (stepy > 0) {
            y1 = modelLayer.getMaxy() + 1;
        }

        int z1 = 0;
        if (stepz < 0) {
            z1 = modelLayer.getMinz() - 1;
        } else if (stepz > 0) {
            z1 = modelLayer.getMaxz() + 1;
        }

        Vector3i pos = new Vector3i();

        if (stepx != 0) {
            for (int dy = 0; dy < this.getHeight(); dy++) {
                for (int dz = 0; dz < this.getDepth(); dz++) {
                    int x0 = this.getX();
                    int y0 = this.getY() + dy;
                    int z0 = this.getZ() + dz;
                    int x;
                    for (x = x0; x != x1; x += stepx) {
                        if (modelLayer.unsetBlockData(pos.set(x, y0, z0))) {
                            updated = true;
                        }
                    }
                }
            }
        }

        if (stepy != 0) {
            for (int dx = 0; dx < this.getWidth(); dx++) {
                for (int dz = 0; dz < this.getDepth(); dz++) {
                    int x0 = this.getX() + dx;
                    int y0 = this.getY();
                    int z0 = this.getZ() + dz;
                    int y;
                    for (y = y0; y != y1; y += stepy) {
                        if (modelLayer.unsetBlockData(pos.set(x0, y, z0))) {
                            updated = true;
                        }
                    }
                }
            }
        }

        if (stepz != 0) {
            for (int dx = 0; dx < this.getWidth(); dx++) {
                for (int dy = 0; dy < this.getHeight(); dy++) {
                    int x0 = this.getX() + dx;
                    int y0 = this.getY() + dy;
                    int z0 = this.getZ();
                    int z;
                    for (z = z0; z != z1; z += stepz) {
                        if (modelLayer.unsetBlockData(pos.set(x0, y0, z))) {
                            updated = true;
                        }
                    }
                }
            }
        }

        return (updated);
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
    public void onLeftReleased() {
    }

    private void updateHoveredBlock() {

        ModelInstance modelInstance = this.guiModelView.getSelectedModelInstance();
        EditableModelLayer modelLayer = this.guiModelView.getSelectedModelLayer();

        // extract objects
        if (modelInstance == null || modelLayer == null) {
            return;
        }
        Entity entity = modelInstance.getEntity();
        float s = modelLayer.getBlockSizeUnit();
        ModelEditorCamera camera = this.getCamera();

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
                (x, y, z, theFace) -> {
                    if (y < 0 || modelLayer.getBlockData(pos.set(x, y, z)) != null) {
                        hovered.set(x, y, z);
                        face = Face.fromVec(theFace);
                        return (true);
                    }
                    return (false);
                });

    }

    @Override
    public void onMouseScroll(GuiEventMouseScroll<GuiModelView> event) {
        if (!super.guiModelView.isLeftPressed()) {
            float speed = this.getCamera().getDistanceFromCenter() * 0.14f;
            this.getCamera().increaseDistanceFromCenter((float) (-event.getScrollY() * speed));
        }
    }

    @Override
    public void onUpdate() {
        this.updateCameraRotation();
        this.updateSelection();
    }

    private void updateSelection() {
        if (!this.guiModelView.isLeftPressed()) {
            this.firstBlock.set(this.hovered);
        }
        this.secondBlock.set(this.hovered.x, this.hovered.y, this.hovered.z);

        Vector3i o0 = BlockRenderer.VERTICES[BlockRenderer.FACES_VERTICES[face.getID()][0]];
        Vector3i o1 = BlockRenderer.VERTICES[BlockRenderer.FACES_VERTICES[face.getID()][1]];
        Vector3i o2 = BlockRenderer.VERTICES[BlockRenderer.FACES_VERTICES[face.getID()][2]];
        Vector3i o3 = BlockRenderer.VERTICES[BlockRenderer.FACES_VERTICES[face.getID()][3]];
        this.quad[0].set(this.getPositionX() + this.getSizeX() * o0.x, this.getPositionY() + this.getSizeY() * o0.y,
                this.getPositionZ() + this.getSizeZ() * o0.z);
        this.quad[1].set(this.getPositionX() + this.getSizeX() * o1.x, this.getPositionY() + this.getSizeY() * o1.y,
                this.getPositionZ() + this.getSizeZ() * o1.z);
        this.quad[2].set(this.getPositionX() + this.getSizeX() * o2.x, this.getPositionY() + this.getSizeY() * o2.y,
                this.getPositionZ() + this.getSizeZ() * o2.z);
        this.quad[3].set(this.getPositionX() + this.getSizeX() * o3.x, this.getPositionY() + this.getSizeY() * o3.y,
                this.getPositionZ() + this.getSizeZ() * o3.z);

        LineRendererFactory factory = super.guiModelView.getWorldRenderer().getLineRendererFactory();
        for (Line line : this.lines) {
            factory.addLine(line);
        }

    }

    private final void updateCameraRotation() {
        // rotate
        if (this.guiModelView.isRightPressed()) {
            float pitch = ((this.guiModelView.getPrevMouseY() - this.guiModelView.getMouseY()) * 64.0f);
            this.getCamera().increasePitch(pitch);

            float angle = ((this.guiModelView.getPrevMouseX() - this.guiModelView.getMouseX()) * 128.0f);
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
        return ("Extrude");
    }

    public Vector3i getFirstBlock() {
        return (this.firstBlock);
    }

    public Vector3i getSecondBlock() {
        return (this.secondBlock);
    }

    public Face getFace() {
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
