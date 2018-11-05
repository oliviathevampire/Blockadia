package team.hdt.blockadia.engine_rewrite.core.entitys;

public class EntityMod extends Entity{

    public void setID(String Mod_ID,String ID) {
        super.setID(Mod_ID + ":" + ID);
    }

    public EntityMod() {
    }

    @Override
    protected void entityInit() {

    }
}
