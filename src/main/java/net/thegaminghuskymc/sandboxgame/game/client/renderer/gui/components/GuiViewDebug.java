/**
 * *	This file is part of the project https://github.com/toss-dev/VoxelEngine
 * *
 * *	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
 * *
 * *	PEREIRA Romain
 * *                                       4-----7
 * *                                      /|    /|
 * *                                     0-----3 |
 * *                                     | 5___|_6
 * *                                     |/    | /
 * *                                     1-----2
 */

package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;


import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraPerspectiveWorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjectiveWorld;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera.ModelEditorCamera;

public class GuiViewDebug extends GuiView {

    private final GuiLabel label;
    private final CameraProjective camera;

    public GuiViewDebug(CameraProjective camera) {
        super();
        this.camera = camera;
        this.label = new GuiLabel();
        this.label.setFontSize(0.5f, 0.5f);
        this.label.setFontColor(Gui.COLOR_BLACK);
        this.addChild(this.label);
        this.label.setHoverable(false);
        this.setHoverable(false);
    }

    @Override
    protected void onUpdate() {
        this.updateText();
    }

    private void updateText() {

        CameraProjective cam = this.camera;

        Vector3f pos = cam.getPosition();
        Vector3f look = cam.getViewVector();

        StringBuilder builder = new StringBuilder();

        if (cam instanceof CameraPerspectiveWorldEntity) {
            WorldEntity entity = ((CameraPerspectiveWorldEntity) cam).getEntity();
            builder.append("Position: ");
            builder.append(Maths.approximatate(entity.getPositionX(), 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(entity.getPositionY(), 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(entity.getPositionZ(), 10.0f));

            builder.append("\n");
            builder.append("Velocity: ");
            builder.append(Maths.approximatate(entity.getPositionVelocityX(), 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(entity.getPositionVelocityY(), 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(entity.getPositionVelocityZ(), 10.0f));

            builder.append("\n");
            builder.append("Acceleration: ");
            builder.append(Maths.approximatate(entity.getPositionAccelerationX(), 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(entity.getPositionAccelerationY(), 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(entity.getPositionAccelerationZ(), 10.0f));
        } else {
            builder.append("Position: ");
            builder.append((int) pos.x);
            builder.append(":");
            builder.append((int) pos.y);
            builder.append(":");
            builder.append((int) pos.z);

            builder.append("\n");
            builder.append("Look: ");
            builder.append(Maths.approximatate(look.x, 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(look.y, 10.0f));
            builder.append(":");
            builder.append(Maths.approximatate(look.z, 10.0f));

            if (cam instanceof CameraProjectiveWorld && ((CameraProjectiveWorld) cam).getWorld() != null) {
                Vector3i windex = ((CameraProjectiveWorld) cam).getWorld()
                        .getTerrainIndex(((CameraProjectiveWorld) cam).getLookCoords());
                builder.append("\n");
                builder.append("Look index: ");
                builder.append(windex.x);
                builder.append(":");
                builder.append(windex.y);
                builder.append(":");
                builder.append(windex.z);

                Vector3f vec = ((CameraProjectiveWorld) cam).getLookCoords();
                builder.append("\n");
                builder.append("Block: ");
                builder.append((int) (vec.x));
                builder.append(":");
                builder.append((int) (vec.y));
                builder.append(":");
                builder.append((int) (vec.z));
            }

            if (cam instanceof ModelEditorCamera) {
                builder.append("\n");
                builder.append(ModelEditorCamera.TOOLS_NAME[((ModelEditorCamera) cam).getToolID()]);
            }
        }

        builder.append("\nFPS: ");
        builder.append(GLH.glhGetContext().getWindow().getFPS());

        builder.append("\nDraw calls: ");
        builder.append(GLH.glhGetContext().getDrawCalls());
        builder.append("\nVertex drawn: ");
        builder.append(GLH.glhGetContext().getVerticesDrawn());
        this.label.setText(builder.toString());
        this.label.setBoxPosition(0.0f, 1.0f - this.label.getTextHeight());
    }

    @Override
    protected void onInitialized(GuiRenderer renderer) {
    }

    @Override
    protected void onDeinitialized(GuiRenderer renderer) {
    }

}