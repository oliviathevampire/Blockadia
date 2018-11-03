package team.hdt.blockadia.engine.core_rewrite.three_d.entitys;

import team.hdt.blockadia.engine.core_rewrite.three_d.AxisAlignedBB3D;

public abstract class EntityItem extends Entity {
    private static AxisAlignedBB3D defalt_hitbox = new AxisAlignedBB3D(0.0D, 0.0D, 0.0D, 0.25D, 0.25D, 0.25D);
    private int health;                                               //            min                         max

    public EntityItem(float x, float y, float z) {
        this.health = 10;
        this.setRunSpeed(0.0F);
        this.setWalkSpeed(0.0F);
        this.setEntityBoundingBox(defalt_hitbox);
        this.setPosition(x, y, z);
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        //TODO: add what to to when hit by player
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    public boolean canBeWornDown() {
        return true;
    }

}
