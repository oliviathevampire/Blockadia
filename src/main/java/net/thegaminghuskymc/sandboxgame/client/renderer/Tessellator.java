package net.thegaminghuskymc.sandboxgame.client.renderer;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Tessellator {
    /**
     * The static instance of the Tessellator.
     */
    private static final Tessellator INSTANCE = new Tessellator(2097152);
    private final BufferBuilder buffer;
    private final WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();

    public Tessellator(int bufferSize) {
        this.buffer = new BufferBuilder(bufferSize);
    }

    public static Tessellator getInstance() {
        return INSTANCE;
    }

    /**
     * Draws the data set up in this tessellator and resets the state to prepare for new drawing.
     */
    public void draw() {
        this.buffer.finishDrawing();
        this.vboUploader.draw(this.buffer);
    }

    public BufferBuilder getBuffer() {
        return this.buffer;
    }
}