package net.thegaminghuskymc.sandboxgame.client.renderer;

import net.thegaminghuskymc.sandboxgame.client.renderer.texture.Stitcher;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StitcherException extends RuntimeException {
    private final Stitcher.Holder holder;

    public StitcherException(Stitcher.Holder holderIn, String message) {
        super(message);
        this.holder = holderIn;
    }
}