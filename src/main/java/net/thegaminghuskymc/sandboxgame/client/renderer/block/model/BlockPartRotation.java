package net.thegaminghuskymc.sandboxgame.client.renderer.block.model;

import net.thegaminghuskymc.sandboxgame.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockPartRotation {
    public final Vector3f origin;
    public final EnumFacing.Axis axis;
    public final float angle;
    public final boolean rescale;

    public BlockPartRotation(Vector3f originIn, EnumFacing.Axis axisIn, float angleIn, boolean rescaleIn) {
        this.origin = originIn;
        this.axis = axisIn;
        this.angle = angleIn;
        this.rescale = rescaleIn;
    }
}