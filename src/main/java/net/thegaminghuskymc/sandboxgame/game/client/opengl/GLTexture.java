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

package net.thegaminghuskymc.sandboxgame.game.client.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class GLTexture implements GLObject {
    /** opengl texture id */
    private final int glID;

    /** texture size */
    private int width;
    private int height;

    private BufferedImage data;

    public GLTexture(int glID) {
        this.glID = glID;
        this.width = 0;
        this.height = 0;
    }

    public GLTexture() {
        this(0);
    }

    @Override
    public void delete() {
        GL11.glDeleteTextures(this.glID);
        this.width = 0;
        this.height = 0;
    }

    public final void setData(Image img, int imWidth, int imHeight) {
        BufferedImage bufferedImage = new BufferedImage(imWidth, imHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        this.setData(bufferedImage);
    }

    public final BufferedImage getData() {
        return (this.data);
    }

    /** set pixels data */
    public final void setData(BufferedImage img) {
        if (img == null) {
            return;
        }

        byte[] pixels = ImageUtils.getImagePixels(img);
        ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length);
        buffer.put(pixels);
        buffer.flip();

        this.data = img;

        this.bind(GL13.GL_TEXTURE0, GL11.GL_TEXTURE_2D);

        this.image2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, img.getWidth(), img.getHeight(), 0, GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE, buffer);

        this.parameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        this.parameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        this.unbind(GL11.GL_TEXTURE_2D);

        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public final int getWidth() {
        return (this.width);
    }

    public final int getHeight() {
        return (this.height);
    }

    public final void bind(int target) {
        GL11.glBindTexture(target, this.glID);
    }

    public final void bind(int texture, int target) {
        GL13.glActiveTexture(texture);
        GL11.glBindTexture(target, this.glID);
    }

    public final void unbind(int target) {
        GL11.glBindTexture(target, 0);
    }

    /**
     * int target, int level, int internalformat, int width, int height, int
     * border, int format, int type, ByteBuffer pixels
     */
    public final void image2D(int target, int level, int internalformat, int width, int height, int border, int format,
                              int type, ByteBuffer pixels) {
        GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    public final void parameteri(int target, int pname, int param) {
        GL11.glTexParameteri(target, pname, param);
    }

    public final void parameterf(int target, int pname, float param) {
        GL11.glTexParameterf(target, pname, param);
    }

    public final int getID() {
        return (this.glID);
    }

    /** generate a mipmap (texture lod) */
    public final void generateMipmap(float bias) {
        this.bind(GL13.GL_TEXTURE0, GL11.GL_TEXTURE_2D);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        this.parameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        this.parameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias);
    }
}
