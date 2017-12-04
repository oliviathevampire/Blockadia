package net.thegaminghuskymc.sandboxgame.engine.world.entity.forces;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;

public class ForceJump extends Force<Entity> {

    @Override
    public void onUpdateResultant(Entity entity, Vector3f resultant) {
        resultant.y += entity.getMass() * ForceGravity.G * 8.0f;
    }
}
