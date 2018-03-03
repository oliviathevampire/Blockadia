package net.thegaminghuskymc.sandboxgame.client.audio;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ISoundEventListener {
    void soundPlay(ISound soundIn, SoundEventAccessor accessor);
}