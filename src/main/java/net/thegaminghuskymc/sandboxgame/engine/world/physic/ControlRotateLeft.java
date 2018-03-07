package net.thegaminghuskymc.sandboxgame.engine.world.physic;


import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;

public class ControlRotateLeft extends Control<WorldEntity> {
    @Override
    public void run(WorldEntity entity, double dt) {
        entity.setRotationY(entity.getRotationY() - 2.0f);
    }
}
