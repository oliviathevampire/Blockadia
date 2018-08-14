package team.hdt.blockadia.game_engine.common.util.math;

public class BlockPos {

    /**
     * An immutable block pos with zero as all coordinates.
     */
    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);

    public static float x, y, z;

    public BlockPos(int xPos, int yPos, int zPos) {
        x = xPos;
        y = yPos;
        z = zPos;
    }

    public static float getX() {
        return x;
    }

    public static float getY() {
        return y;
    }

    public static float getZ() {
        return z;
    }

}