package net.thegaminghuskymc.sandboxgame.engine.client.audio;

public interface ISoundEventListener
{
    void soundPlay(ISound soundIn, SoundEventAccessor accessor);
}