package team.hdt.blockadia.engine_rewrite.core.render;


import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.engine_rewrite.Display;
import team.hdt.blockadia.engine_rewrite.core.utils.Matrix4fs;

public class MaterRenderer extends AbstractRender {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private static final float RED = 0.0f;
    private static final float GREEN = 0.0f;
    private static final float BLUE = 0.0f;
    public MainShader program = new MainShader();
    private Matrix4fs projectionMatrix;

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
        program.create();
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4fs();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    @Override
    public void register() {

    }

    @Override
    public void render() {
        prepare();

    }

    @Override
    public void update() {

    }

    @Override
    public void cleanup() {
        program.remove();
    }
}
