package net.thegaminghuskymc.sandboxgame.engine.world.entity.ai;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;

public abstract class EntityAI {

    /**
     * the entity for this ai
     */
    private final Entity entity;

    /**
     * in seconds, accumulate times since last update
     */
    private double accumulator;

    /**
     * time needed to accumulate before updating
     */
    private double updateTime;

    public EntityAI(Entity entity) {
        this.entity = entity;
        this.accumulator = 0;
        this.updateTime = Double.MAX_VALUE;
    }

    /**
     * called on every entity's update
     */
    public final void update(double dt) {
        this.onUpdate(dt);
        this.accumulator += dt;
        int n = (int) (this.accumulator / this.updateTime);
        if (n > 0) {
            do {
                --n;
                this.onTimedUpdate();
            } while (n > 0);
            this.accumulator -= n * this.updateTime;
        }
    }

    /**
     * called on every entity's update
     *
     * @param dt
     */
    protected abstract void onUpdate(double dt);

    /**
     * called until the time stored in {@link #accumulator} <
     * {@link #updateTime} needed by the accumulator
     */
    protected abstract void onTimedUpdate();

    public final double getUpdateTime() {
        return (this.updateTime);
    }

    /**
     * set the time on which the update function should be called
     */
    public final void setUpdateTime(double time) {
        this.updateTime = time;
    }

    public final double getAccumulator() {
        return (this.accumulator);
    }

    public final Entity getEntity() {
        return (this.entity);
    }
}
