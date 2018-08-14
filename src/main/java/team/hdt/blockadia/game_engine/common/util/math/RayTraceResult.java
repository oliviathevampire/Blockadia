package team.hdt.blockadia.game_engine.common.util.math;

import team.hdt.blockadia.game_engine.common.entity.BaseEntity;
import team.hdt.blockadia.game_engine.common.util.EnumFacing;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3d;

public class RayTraceResult {
    /**
     * Used to determine what sub-segment is hit
     */
    public int subHit = -1;

    /**
     * Used to add extra hit info
     */
    public Object hitInfo = null;
    /**
     * The type of hit that occured, see for possibilities.
     */
    public RayTraceResult.Type typeOfHit;
    public EnumFacing sideHit;
    /**
     * The vector position of the hit
     */
    public Vectors3d hitVec;
    /**
     * The hit entity
     */
    public BaseEntity entityHit;
    private BlockPos blockPos;

    public RayTraceResult(Vectors3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, blockPosIn);
    }

    public RayTraceResult(Vectors3d hitVecIn, EnumFacing sideHitIn) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, BlockPos.ORIGIN);
    }

    public RayTraceResult(BaseEntity entityIn) {
        this(entityIn, new Vectors3d(entityIn.positionX, entityIn.positionY, entityIn.positionZ));
    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vectors3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn) {
        this.typeOfHit = typeIn;
        this.blockPos = blockPosIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Vectors3d(hitVecIn.x, hitVecIn.y, hitVecIn.z);
    }

    public RayTraceResult(BaseEntity entityHitIn, Vectors3d hitVecIn) {
        this.typeOfHit = RayTraceResult.Type.ENTITY;
        this.entityHit = entityHitIn;
        this.hitVec = hitVecIn;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }

    public static enum Type {
        MISS,
        BLOCK,
        ENTITY
    }

}