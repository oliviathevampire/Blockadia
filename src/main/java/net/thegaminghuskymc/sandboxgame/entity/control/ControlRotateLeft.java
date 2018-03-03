package net.thegaminghuskymc.sandboxgame.entity.control;

import net.thegaminghuskymc.sandboxgame.entity.Entity;

public class ControlRotateLeft extends Control<Entity> {
    @Override
    public void run(Entity entity, double dt) {
        entity.setRotationY(entity.getRotationY() - 2.0f);
    }
}
