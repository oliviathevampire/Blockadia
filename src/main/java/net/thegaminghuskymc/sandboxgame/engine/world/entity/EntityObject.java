package net.thegaminghuskymc.sandboxgame.engine.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.world.World;

public abstract class EntityObject extends Entity {

    public EntityObject(World world) {
        super(world);
    }

    @Override
    protected void onUpdate(double dt) {
    }
}
