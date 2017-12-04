package net.thegaminghuskymc.sandboxgame.engine.world.entity.forces;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.collision.PhysicObject;

public abstract class Force<T extends PhysicObject> {

    /**
     * gravity
     */
    public static final ForceGravity GRAVITY = new ForceGravity();
    public static final ForceFriction FRICTION = new ForceAirFriction();
    public static final ForceJump JUMP = new ForceJump();

    public final void updateResultant(T object, Vector3f resultant) {
        this.onUpdateResultant(object, resultant);
    }

    /**
     * called when this state is set and the object is updated.
     */
    public abstract void onUpdateResultant(T object, Vector3f resultant);
}