package net.timmy.timmylib.utils;

import net.thegaminghuskymc.sandboxgame.util.math.AxisAlignedBB;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.util.math.Vec3d;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

public class LibIndexedAABB extends AxisAlignedBB {

    public final int index;

    public LibIndexedAABB(AxisAlignedBB aabb, int index) {
        super(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
        this.index = index;
    }

    public LibIndexedAABB(double x1, double y1, double z1, double x2, double y2, double z2, int index) {
        super(x1, y1, z1, x2, y2, z2);
        this.index = index;
    }

    public LibIndexedAABB(BlockPos pos, int index) {
        super(pos);
        this.index = index;
    }

    public LibIndexedAABB(BlockPos pos1, BlockPos pos2, int index) {
        super(pos1, pos2);
        this.index = index;
    }

    @SideOnly(Side.CLIENT)
    public LibIndexedAABB(Vec3d min, Vec3d max, int index) {
        super(min, max);
        this.index = index;
    }

}