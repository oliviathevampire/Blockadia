package net.thegaminghuskymc.sandboxgame.network.datasync;

import net.thegaminghuskymc.sandboxgame.network.PacketBuffer;

import java.io.IOException;

public interface DataSerializer<T>
{
    void write(PacketBuffer buf, T value);

    T read(PacketBuffer buf) throws IOException;

    DataParameter<T> createKey(int id);

    T copyValue(T value);
}