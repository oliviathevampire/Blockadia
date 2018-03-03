package net.thegaminghuskymc.sandboxgame.entity.forces;

import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.util.math.Vector3f;

public class ForceJump extends Force<Entity> {

    @Override
    public void onUpdateResultant(Entity entity, Vector3f resultant) {
        resultant.y += entity.getMass() * ForceGravity.G * 8.0f;
    }
}
