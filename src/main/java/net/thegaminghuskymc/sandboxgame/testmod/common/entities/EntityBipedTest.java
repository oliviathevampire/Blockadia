package net.thegaminghuskymc.sandboxgame.testmod.common.entities;

import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntityBiped;

public class EntityBipedTest extends WorldEntityBiped {

    public EntityBipedTest(World world) {
        super(world, 50, 1.0f, 2.0f, 1.0f);
    }

    @Override
    protected void onUpdate(double dt) {
        World world = this.getWorld();
        if (world == null) {
            return;
        }
        float x = this.getPositionX() + this.getSizeX() * 0.5f;
        float y = this.getPositionY() - 1.0f;
        float z = this.getPositionZ() + this.getSizeX() * 0.5f;
        world.setBlockDurability((byte) ((System.currentTimeMillis() % 2000) / 100), x, y, z);
    }

}