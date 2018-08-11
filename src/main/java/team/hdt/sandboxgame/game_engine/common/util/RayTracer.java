package team.hdt.sandboxgame.game_engine.common.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class RayTracer {

    private RayTracer() {

    }

    public static Ray getScreenCenterRay() {
        float winX = Display.getWidth() / 2, winY = Display.getHeight() / 2;
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);
        FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelview);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, projection);
        FloatBuffer positionNear = BufferUtils.createFloatBuffer(3);
        FloatBuffer positionFar = BufferUtils.createFloatBuffer(3);
        //TODO: Fix this
//        GLU.gluUnProject(winX, winY, 0, modelview, projection, viewport, positionNear);
//        GLU.gluUnProject(winX, winY, 1, modelview, projection, viewport, positionFar);
        Vectors3f nearVec = new Vectors3f(positionNear.get(0), positionNear.get(1), positionNear.get(2));
        Vectors3f farVec = new Vectors3f(positionFar.get(0), positionFar.get(1), positionFar.get(2));
        return new Ray(nearVec, Vectors3f.sub(farVec, nearVec, null).normalise(null), .1f);
    }

}