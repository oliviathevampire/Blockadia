package net.thegaminghuskymc.sandboxgame.testmod.common.entities;

import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.EntityBiped;

public class EntityBipedTest extends EntityBiped {

    public EntityBipedTest(World world) {
        super(world);
        super.setMass(100);
    }

    @Override
    protected void onUpdate(double dt) {

    }

}