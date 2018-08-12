package team.hdt.sandboxgame.game_engine.common.world.player;

import org.lwjgl.BufferUtils;
import team.hdt.sandboxgame.game_engine.common.Main;
import team.hdt.sandboxgame.game_engine.common.util.Display;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static team.hdt.sandboxgame.game_engine.common.util.Project.gluPerspective;

public class Camera {

    public float x, y, z;
    float yaw = 0;
    private float pitch = 0;
    private float aspectRatio;

    private FloatBuffer perspectiveProjectionMatrix = BufferUtils.createFloatBuffer(16);
    private FloatBuffer orthographicProjectionMatrix = BufferUtils.createFloatBuffer(16);

    Camera(int x, int y, int z) {
        this.aspectRatio = (float) Main.WINDOW_WIDTH / Main.WINDOW_HEIGHT;
        this.x = x + .5f;
        this.y = y + 1.5f;
        this.z = z + .5f;
    }

    void setup() {
        glPushAttrib(GL_TRANSFORM_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float fov = 70;
        float zNear = .1f;
        float zFar = 300;
        gluPerspective(fov, aspectRatio, zNear, zFar);
        glPopAttrib();

        glGetFloatv(GL_PROJECTION_MATRIX, perspectiveProjectionMatrix);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glGetFloatv(GL_PROJECTION_MATRIX, orthographicProjectionMatrix);
        glLoadMatrixf(perspectiveProjectionMatrix);
        glMatrixMode(GL_MODELVIEW);

        yaw = 180;
    }

    public void update() {
        glPushAttrib(GL_TRANSFORM_BIT);
        glMatrixMode(GL_MODELVIEW);
        glRotatef(-pitch, 1, 0, 0);
        glRotatef(yaw, 0, 1, 0);
        glTranslatef(-x, -y, -z);
        glPopAttrib();
    }

    boolean processMouse() {
        float mouseDX = (float) (Main.display.getMouseX() * .75f * 0.16f);
        float mouseDY = (float) (Main.display.getMouseY() * .75f * 0.16f);
        if (mouseDX == 0 && mouseDY == 0)
            return false;
        yaw = (yaw + mouseDX) % 360;
        if (yaw < 0)
            yaw += 360;
        if (pitch + mouseDY >= (float) -80 && pitch + mouseDY <= (float) 90)
            pitch += mouseDY;
        else if (pitch + mouseDY > (float) 90)
            pitch = (float) 90;
        else if (pitch + mouseDY < (float) -80)
            pitch = (float) -80;
        return true;
    }

}
