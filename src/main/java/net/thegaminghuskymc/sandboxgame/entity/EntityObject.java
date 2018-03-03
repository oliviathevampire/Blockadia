package net.thegaminghuskymc.sandboxgame.entity;

import net.thegaminghuskymc.sandboxgame.world.World;

public abstract class EntityObject extends Entity {

    public EntityObject(World world) {
        super(world);
    }

    @Override
    protected void onUpdate(double dt) {
    }
}
