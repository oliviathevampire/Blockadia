package net.thegaminghuskymc.sandboxgame.engine.client.audio;

public interface ISoundEventAccessor<T>
{
    int getWeight();

    T cloneEntry();
}