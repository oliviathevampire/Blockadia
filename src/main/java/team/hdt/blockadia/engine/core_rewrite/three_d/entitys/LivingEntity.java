package team.hdt.blockadia.engine.core_rewrite.three_d.entitys;

public abstract class LivingEntity extends Entity {

    public LivingEntity() {
    }

    @Override
    public boolean canBeWornDown() {
        return false;
    }

    @Override
    public boolean canBeHurt() {
        return true;
    }

    @Override
    protected void entityInit() {

    }

    public void onUpdate() {

    }

    public void onLivingUpdate() {
    }

    protected void initEntityAI() {
    }

    protected boolean canEquipItem() {
        return true;
    }

    protected boolean canDespawn() {
        return true;
    }

    public enum SpawnPlacementType {
        UNDER_GROUND,
        ABOVE_GROUND,
        BELOW_WATER,
        ABOVE_WATER,
        IN_AIR
    }
}
