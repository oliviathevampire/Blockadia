package net.thegaminghuskymc.sandboxgame.engine.util.math;

import net.thegaminghuskymc.sandboxgame.engine.entities.Entity;
import net.thegaminghuskymc.sandboxgame.engine.util.EnumFacing;

public class RayTraceResult
{
    public int subHit = -1;

    public Object hitInfo = null;

    private BlockPos blockPos;
    public RayTraceResult.Type typeOfHit;
    public EnumFacing sideHit;
    public Vec3d hitVec;
    public Entity entityHit;

    public RayTraceResult(Vec3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn)
    {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, blockPosIn);
    }

    public RayTraceResult(Vec3d hitVecIn, EnumFacing sideHitIn)
    {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, BlockPos.ORIGIN);
    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vec3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn)
    {
        this.typeOfHit = typeIn;
        this.blockPos = blockPosIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Vec3d(hitVecIn.x, hitVecIn.y, hitVecIn.z);
    }

    public RayTraceResult(Entity entityHitIn, Vec3d hitVecIn)
    {
        this.typeOfHit = RayTraceResult.Type.ENTITY;
        this.entityHit = entityHitIn;
        this.hitVec = hitVecIn;
    }

    public BlockPos getBlockPos()
    {
        return this.blockPos;
    }

    public String toString()
    {
        return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }

    public enum Type
    {
        MISS,
        BLOCK,
        ENTITY
    }
}