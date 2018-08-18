package team.hdt.blockadia.game_engine_old.client.rendering;

import team.hdt.blockadia.game_engine_old.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine_old.client.rendering.fontRendering.FontRenderer;
import team.hdt.blockadia.game_engine_old.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine_old.client.rendering.guiRendering.GuiRenderer;
import team.hdt.blockadia.game_engine_old.common.CameraInterface;
import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.core.world.misc.EnvironmentVariables;

public class MasterRenderer {

    private static final int BLUR_TEXTURE_WIDTH = 384;
    private static final int BLUR_TEXTURE_HEIGHT = 216;

    private static final Matrix4fs projectionMatrix = new Matrix4fs();
    private static final Matrix4fs projectionViewMatrix = new Matrix4fs();

    private static FontRenderer fontRenderer;
    private static GuiRenderer guiRenderer;

    public static void init(CameraInterface camera) {
        createProjectionMatrix(camera);
        fontRenderer = new FontRenderer();
        guiRenderer = new GuiRenderer();
    }

    /**
     * Cleans up all of the render processes. Should be called when the game
     * closes.
     */
    public static void cleanUp() {
        fontRenderer.cleanUp();
        guiRenderer.cleanUp();
    }

    /**
     * @return The projection matrix currently being used.
     */
    public static Matrix4fs getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * @return the projection matrix multiplied by the view matrix.
     */
    public static Matrix4fs getProjectionViewMatrix() {
        return projectionViewMatrix;
    }

    public static void renderGuis() {
        GuiRenderData renderData = GuiMaster.getRenderData();
        for (GuiRenderData.GuiRenderLevelData renderLevel : renderData.getRenderData()) {
            if (!renderLevel.isEmpty()) {
                guiRenderer.render(renderLevel.getTextures());
                fontRenderer.render(renderLevel.getTexts());
            }
        }
    }

    /**
     * The master render method which is called once per frame and carries out
     * all the necessary rendering for each frame.
     */
    public static void render() {
        CameraInterface camera = EngineMaster.getCamera();
        EnvironmentVariables.getVariables().update();
        Matrix4fs.mul(projectionMatrix, camera.getViewMatrix(), projectionViewMatrix);
    }

    /**
     * Creates the projection matrix based on the view frustum described by the
     * camera.
     *
     * @param camera - the camera to be used in the creation of the view frustum.
     */
    private static void createProjectionMatrix(CameraInterface camera) {
        float farPlane = camera.getFarPlane();
        float nearPlane = camera.getNearPlane();
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(camera.getFOV() / 2f))));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farPlane - nearPlane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
        projectionMatrix.m33 = 0;
    }

}
