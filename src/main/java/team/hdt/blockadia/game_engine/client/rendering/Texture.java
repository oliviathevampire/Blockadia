package team.hdt.blockadia.game_engine.client.rendering;

import java.nio.ByteBuffer;

public class Texture {

    public ByteBuffer buffer;
    public int width, height;

    public Texture(ByteBuffer buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

}