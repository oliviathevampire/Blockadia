package net.thegaminghuskymc.sandboxgame.game.client.renderer;

import java.nio.ByteBuffer;

public abstract class MeshVertex {

    public MeshVertex() {
    }

    public abstract void store(ByteBuffer buffer);

    public abstract MeshVertex clone();

}
