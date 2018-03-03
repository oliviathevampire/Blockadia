package net.thegaminghuskymc.sandboxgame.client.renderer.vertex;

import net.thegaminghuskymc.sandboxgame.client.renderer.GlStateManager;
import net.thegaminghuskymc.sandboxgame.client.renderer.OpenGlHelper;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.nio.ByteBuffer;

@SideOnly(Side.CLIENT)
public class VertexBuffer {
    private final VertexFormat vertexFormat;
    private int glBufferId;
    private int count;

    public VertexBuffer(VertexFormat vertexFormatIn) {
        this.vertexFormat = vertexFormatIn;
        this.glBufferId = OpenGlHelper.glGenBuffers();
    }

    public void bindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
    }

    public void bufferData(ByteBuffer data) {
        this.bindBuffer();
        OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, data, 35044);
        this.unbindBuffer();
        this.count = data.limit() / this.vertexFormat.getSize();
    }

    public void drawArrays(int mode) {
        GlStateManager.glDrawArrays(mode, 0, this.count);
    }

    public void unbindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
    }

    public void deleteGlBuffers() {
        if (this.glBufferId >= 0) {
            OpenGlHelper.glDeleteBuffers(this.glBufferId);
            this.glBufferId = -1;
        }
    }
}