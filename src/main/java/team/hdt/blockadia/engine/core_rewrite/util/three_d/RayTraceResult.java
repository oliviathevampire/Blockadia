package team.hdt.blockadia.engine.core_rewrite.util.three_d;

import ga.pheonix.utillib.utils.world.EnumFacing;

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
     * The type of hit that occured, see  for possibilities.
     */
    public RayTraceResult.Type typeOfHit;
    public EnumFacing sideHit;
    /**
     * The vector position of the hit
     */
    public Vec3d hitVec;
    /**
     * The hit entity
     */
    public Entity entityHit;

    public RayTraceResult(Vec3d hitVecIn, EnumFacing sideHitIn) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn);
    }

    public RayTraceResult(Entity entityIn) {
        this(entityIn, new Vec3d(entityIn.getPosition().x, entityIn.getPosition().y, entityIn.getPosition().z));
    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vec3d hitVecIn, EnumFacing sideHitIn) {
        this.typeOfHit = typeIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Vec3d(hitVecIn.x, hitVecIn.y, hitVecIn.z);
    }

    public RayTraceResult(Entity entityHitIn, Vec3d hitVecIn) {
        this.typeOfHit = RayTraceResult.Type.ENTITY;
        this.entityHit = entityHitIn;
        this.hitVec = hitVecIn;
    }

    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }

    public enum Type {
        MISS,
        BLOCK,
        ENTITY
    }
}

