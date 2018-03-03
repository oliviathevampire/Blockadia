package net.thegaminghuskymc.sandboxgame.entity.forces;

import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.util.math.Vector3f;

public class ForceGravity extends Force<Entity> {

    /**
     * a force applied to the entity which attract it down
     */
    // g = 9.81 m.s^-2 scaled
    public static final float G = 9.81f;

    @Override
    public void onUpdateResultant(Entity entity, Vector3f resultant) {
        // F = mg
        resultant.y -= entity.getMass() * G;

    }
}
