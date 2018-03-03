package net.thegaminghuskymc.sandboxgame.client.renderer.culling;

import net.thegaminghuskymc.sandboxgame.util.math.AxisAlignedBB;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ICamera {
    /**
     * Returns true if the bounding box is inside all 6 clipping planes, otherwise returns false.
     */
    boolean isBoundingBoxInFrustum(AxisAlignedBB p_78546_1_);

    void setPosition(double xIn, double yIn, double zIn);
}