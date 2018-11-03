package team.hdt.blockadia.engine.core_rewrite.three_d.entitys;

import team.hdt.blockadia.engine.core_rewrite.three_d.AxisAlignedBB3D;

public class EntityPlayer extends LivingEntity {

    public static int health;
    public static float attackdamage;
    public static float defence;                                      //            min                        max
    private static AxisAlignedBB3D player_hitbox = new AxisAlignedBB3D(0.0D, 0.0D, 0.0D, 1.0D, 2.5D, 07.5D);

    public EntityPlayer() {
        health = 20;
        attackdamage = 2.1F;
        /**
         * this is only ro go from 0 to 1
         */
        defence = 0.2F;
        this.setWalkSpeed(0.5F);
        this.setRunSpeed(1.0F);
        this.setEntityBoundingBox(player_hitbox);
    }

    protected void updateAITasks() {
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }


    //TODO:create player
}
