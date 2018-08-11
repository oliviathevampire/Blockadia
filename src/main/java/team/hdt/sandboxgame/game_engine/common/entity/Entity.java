package team.hdt.sandboxgame.game_engine.common.entity;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.world.Arena;

public abstract class Entity {
    public float x, y, z;
    public float yaw = 0;
    public float fallSpeed;
    public Vectors3f momentum;
    public float accel = 0, speed;
    public Arena arena;

    public Entity(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.momentum = new Vectors3f();
    }

    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @return the z
     */
    public float getZ() {
        return z;
    }

    public abstract void update();

    public abstract void render();

}