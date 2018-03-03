package net.thegaminghuskymc.sandboxgame.client.audio;

import net.thegaminghuskymc.sandboxgame.util.SoundCategory;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public interface ISound {
    ResourceLocation getSoundLocation();

    @Nullable
    SoundEventAccessor createAccessor(SoundHandler handler);

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

    @SideOnly(Side.CLIENT)
    public static enum AttenuationType {
        NONE(0),
        LINEAR(2);

        private final int type;

        private AttenuationType(int typeIn) {
            this.type = typeIn;
        }

        public int getTypeInt() {
            return this.type;
        }
    }
}