package team.hdt.sandboxgame.game_engine.common.world.player;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import team.hdt.sandboxgame.game_engine.common.Main;
import team.hdt.sandboxgame.game_engine.common.util.Display;

import java.awt.*;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static team.hdt.sandboxgame.game_engine.common.util.Project.gluPerspective;

public class Camera {

    public float x = 0, y = 0, z = 0;
    float yaw = 0;
    float pitch = 0;
    private int mouseSpeed = 1;
    private float fov = 70;
    private float aspectRatio;
    private float zNear = .1f;
    private float zFar = 300;

    private boolean wire;

    private Player player;

    private UnicodeFont font;
    private FloatBuffer perspectiveProjectionMatrix = BufferUtils.createFloatBuffer(16);
    private FloatBuffer orthographicProjectionMatrix = BufferUtils.createFloatBuffer(16);
    private DecimalFormat formatter = new DecimalFormat("#.##");

    public Camera(Player player, int x, int y, int z) {
//		setUpFonts();
        this.aspectRatio = (float) Main.WINDOW_WIDTH / Main.WINDOW_HEIGHT;
        this.player = player;
        this.x = x + .5f;
        this.y = y + 1.5f;
        this.z = z + .5f;
    }

    public void setup() {
        glPushAttrib(GL_TRANSFORM_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
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

    @SuppressWarnings("unchecked")
    private void setUpFonts() {
        java.awt.Font awtFont = new java.awt.Font("Chalkduster", java.awt.Font.BOLD, 18);
        font = new UnicodeFont(awtFont);
        font.getEffects().add(new ColorEffect(Color.white));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

	/*public void drawString(int x, int y, String string) {
		glMatrixMode(GL_PROJECTION);
		glLoadMatrixf(orthographicProjectionMatrix);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glPushAttrib(GL_ALL_ATTRIB_BITS);
		glLoadIdentity();
		font.drawString(x, y, string);
		glPopAttrib();
		glPopMatrix();
		glMatrixMode(GL_PROJECTION);
		glLoadMatrixf(perspectiveProjectionMatrix);
		glMatrixMode(GL_MODELVIEW);
	}*/

    public void update() {
        glPushAttrib(GL_TRANSFORM_BIT);
        glMatrixMode(GL_MODELVIEW);
        glRotatef(-pitch, 1, 0, 0);
        glRotatef(yaw, 0, 1, 0);
        glTranslatef(-x, -y, -z);
        glPopAttrib();
    }

    public void drawDebug() {
		/*drawString(10, 110, "Pitch: " + pitch + " Yaw: " + yaw);
		drawString(10, 90, "Player[x=" + formatter.format(player.x) + ",y=" + formatter.format(player.y) + ",z=" + formatter.format(player.z) + "]");
		drawString(10, 70, "Location: Cam[x=" + formatter.format(x) + ",y=" + formatter.format(y) + ",z=" + formatter.format(z) + "]");
		drawString(10, 50, "Flat Arena with a floating block for 360 degree view");
		drawString(10, 30, "Click to grab mouse, right click to release");
		drawString(10, 10, "WASD to move");*/
    }

    public void processMouse() {
        final float MAX_LOOK_UP = 100;
        final float MAX_LOOK_DOWN = -90;
        float mouseDX = (float) (Main.display.getMouseX() * 0.16f);
        float mouseDY = (float) (Main.display.getMouseY() * 0.16f);
        if (yaw + mouseDX >= 360) {
            yaw = yaw + mouseDX - 360;
        } else if (yaw + mouseDX < 0) {
            yaw = 360 - yaw + mouseDX;
        } else {
            yaw += mouseDX;
        }
        if (pitch - mouseDY >= MAX_LOOK_DOWN && pitch - mouseDY <= MAX_LOOK_UP) {
            pitch += -mouseDY;
        } else if (pitch - mouseDY < MAX_LOOK_DOWN) {
            pitch = MAX_LOOK_DOWN;
        } else if (pitch - mouseDY > MAX_LOOK_UP) {
            pitch = MAX_LOOK_UP;
        }
    }

    public boolean processMouse(float mouseSpeed, float maxLookUp, float maxLookDown) {
        float mouseDX = (float) (Main.display.getMouseX() * mouseSpeed * 0.16f);
        float mouseDY = (float) (Main.display.getMouseY() * mouseSpeed * 0.16f);
        if (mouseDX == 0 && mouseDY == 0)
            return false;
        yaw = (yaw + mouseDX) % 360;
        if (yaw < 0)
            yaw += 360;
        if (pitch + mouseDY >= maxLookDown && pitch + mouseDY <= maxLookUp)
            pitch += mouseDY;
        else if (pitch + mouseDY > maxLookUp)
            pitch = maxLookUp;
        else if (pitch + mouseDY < maxLookDown)
            pitch = maxLookDown;
        return true;
    }

    void up(float dy) {
        y += dy;
    }

    void down(float dy) {
        y -= dy;
    }

    void fall(float dy) {
        if (this.y > (int) this.y && this.y - dy < (int) this.y)
            this.y = (int) this.y;
        else
            this.y -= dy;
    }

    void move(float x, float z) {
        this.x = x;
        this.z = z;
    }

    void moveFromLook(float dx, float dy, float dz) {
        this.z += dx * (float) cos(toRadians(yaw - 90)) + dz * cos(toRadians(yaw));
        this.x -= dx * (float) sin(toRadians(yaw - 90)) + dz * sin(toRadians(yaw));
        this.y += dy * (float) sin(toRadians(pitch - 90)) + dz * sin(toRadians(pitch));
    }
}