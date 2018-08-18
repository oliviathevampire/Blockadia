package team.hdt.blockadia.game_engine_old.util.toolbox;

import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;

public class GridAABB {

    public final Vectors3f mins;
    public final Vectors3f maxs;
    public final Vectors3f center;

    public GridAABB(Vectors3f mins, Vectors3f maxs) {
        this.mins = mins;
        this.maxs = maxs;
        this.center = calculateCenter();
    }

    public float getSizeX() {
        return maxs.x - mins.x;
    }

    public float getSizeY() {
        return maxs.y - mins.y;
    }

    public float getSizeZ() {
        return maxs.z - mins.z;
    }

    private Vectors3f calculateCenter() {
        float x = mins.x + getSizeX() * 0.5f;
        float y = mins.y + getSizeY() * 0.5f;
        float z = mins.z + getSizeZ() * 0.5f;
        return new Vectors3f(x, y, z);
    }
}
