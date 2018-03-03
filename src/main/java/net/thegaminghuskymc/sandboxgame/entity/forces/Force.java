package net.thegaminghuskymc.sandboxgame.entity.forces;

import net.thegaminghuskymc.sandboxgame.entity.collision.PhysicObject;
import net.thegaminghuskymc.sandboxgame.util.math.Vector3f;

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