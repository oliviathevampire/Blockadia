package net.thegaminghuskymc.sandboxgame.engine.client.audio;

import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;
import net.thegaminghuskymc.sandboxgame.engine.util.SoundCategory;
import org.jetbrains.annotations.Nullable;

public interface ISound
{
    ResourceLocation getSoundLocation();

    Sound getSound();

    SoundCategory getCategory();

    boolean canRepeat();

    int getRepeatDelay();

    float getVolume();

    float getPitch();

    float getXPosF();

    float getYPosF();

    float getZPosF();

    ISound.AttenuationType getAttenuationType();

    enum AttenuationType
    {
        NONE(0),
        LINEAR(2);

        private final int type;

        AttenuationType(int typeIn)
        {
            this.type = typeIn;
        }

        public int getTypeInt()
        {
            return this.type;
        }
    }
}