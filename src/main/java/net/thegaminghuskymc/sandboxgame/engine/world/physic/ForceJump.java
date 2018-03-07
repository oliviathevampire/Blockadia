package net.thegaminghuskymc.sandboxgame.engine.world.physic;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;

public class ForceJump extends Force<WorldEntity> {

    @Override
    public void onUpdateResultant(WorldEntity entity, Vector3f resultant) {
        resultant.z += entity.getMass() * ForceGravity.G * 8.0f;
    }
}
