package team.hdt.sandboxgame.game_engine.client;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import team.hdt.sandboxgame.game_engine.common.util.Display;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class BitmapFont {

    private static FloatBuffer orthographicProjectionMatrix;
    private int fontTexture;
    private FloatBuffer perspectiveProjectionMatrix;

    /**
     * A Bitmap font ready to be drawn
     *
     * @param fontLoc - Location where font .png is
     */
    public BitmapFont(String fontLoc) {
        perspectiveProjectionMatrix = BufferUtils.createFloatBuffer(16);
        orthographicProjectionMatrix = BufferUtils.createFloatBuffer(16);
        glMatrixMode(GL_PROJECTION);
        glGetFloatv(GL_PROJECTION_MATRIX, perspectiveProjectionMatrix);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glGetFloatv(GL_PROJECTION_MATRIX, orthographicProjectionMatrix);
        glLoadMatrixf(perspectiveProjectionMatrix);
        glMatrixMode(GL_MODELVIEW);
        setUpTextures(fontLoc);
    }

    private static void renderString(String string, int fontTexture, int gridSize, float x, float y, float characterWidth, float characterHeight) {
        glPushAttrib(GL_TEXTURE_BIT | GL_ENABLE_BIT | GL_COLOR_BUFFER_BIT);
        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, fontTexture);
        // Enable linear texture filtering for smoothed results
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        // Enable additive blending. This means that the colors will be added to already existing colors
        // in the frame buffer. In practice, this makes the black parts of the texture become invisible
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);

        // Store the current modelview matrix
        glPushMatrix();
        // Offset all subsequent (at least up until glPopMatrix) vertex coordinates
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();

//		glColor3f(1, 0, 0);
        //glMatrixMode(GL_MODELVIEW);
        glTranslatef(x, y, 0);
        glBegin(GL_QUADS);
        // Iterate over all the characters in the string)
        for (int i = 0; i < string.length(); i++) {
            // Get the ASCII code of the character by type-casting to int
            int asciiCode = (int) string.charAt(i);
            // There are 16 cells in a texture, and a texture coordinate ranges from 0.0 to 1.0
            final float cellSize = 1.0f / gridSize;
            // The cell's x-coordinate is the greates integer smaller than the remainder of the ASCII-code divided by the amount of cells on the x-axis, times the cell size
            float cellX = ((int) asciiCode % gridSize) * cellSize;
            // The cell's y-coordinate is the greatest integer smaller than the ASCII code divied by the amount of cells on the y-axis
            float cellY = ((int) asciiCode / gridSize) * cellSize;
            glTexCoord2f(cellX, cellY + cellSize);
            glVertex2f(i * characterWidth / 3, y);

            glTexCoord2f(cellX + cellSize, cellY + cellSize);
            glVertex2f(i * characterWidth / 3 + characterWidth / 2, y);

            glTexCoord2f(cellX + cellSize, cellY);
            glVertex2f(i * characterWidth / 3 + characterWidth / 2, y + characterHeight);

            glTexCoord2f(cellX, cellY);
            glVertex2f(i * characterWidth / 3, y + characterHeight);
        }
        glEnd();
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
        glPopAttrib();
    }

    private void setUpTextures(String fontLoc) {
        fontTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, fontTexture);
        PNGDecoder decoder = null;
        ByteBuffer buffer = null;
        try {
            decoder = new PNGDecoder(new FileInputStream(fontLoc));
            buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
        } catch (IOException e) {
            System.err.println("Could not find font.");
            System.exit(1);
        }
        // Load the loaded texture data into the texture obj
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        // Unbind the texture
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void draw(String string, float x, float y, float characterWidth, float characterHeight) {
        glMatrixMode(GL_PROJECTION); // For text drawing
        glLoadMatrixf(orthographicProjectionMatrix);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity(); // Clears matrix
        renderString(string, fontTexture, 16, x, y, characterWidth, characterHeight);
        glPopMatrix();
        glMatrixMode(GL_PROJECTION);
        glLoadMatrixf(perspectiveProjectionMatrix);
        glMatrixMode(GL_MODELVIEW);
    }

}
