package team.hdt.blockadia.game_engine.common.util.math.vectors.interfaces;

import com.google.common.base.MoreObjects;
import team.hdt.blockadia.game_engine.common.util.interfaces.Immutable;
import team.hdt.blockadia.game_engine.common.util.math.Maths;


@Immutable
public class Vectors3i implements Comparable<Vectors3i> {
    /**
     * An immutable vector with zero as all coordinates.
     */
    public static final Vectors3i NULL_VECTOR = new Vectors3i(0, 0, 0);
    /**
     * X coordinate
     */
    private final int x;
    /**
     * Y coordinate
     */
    private final int y;
    /**
     * Z coordinate
     */
    private final int z;

    public Vectors3i(int xIn, int yIn, int zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public Vectors3i(double xIn, double yIn, double zIn) {
        this(Maths.floor(xIn), Maths.floor(yIn), Maths.floor(zIn));
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        } else if (!(p_equals_1_ instanceof Vectors3i)) {
            return false;
        } else {
            Vectors3i vectors3I = (Vectors3i) p_equals_1_;

            if (this.getX() != vectors3I.getX()) {
                return false;
            } else if (this.getY() != vectors3I.getY()) {
                return false;
            } else {
                return this.getZ() == vectors3I.getZ();
            }
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int compareTo(Vectors3i p_compareTo_1_) {
        if (this.getY() == p_compareTo_1_.getY()) {
            return this.getZ() == p_compareTo_1_.getZ() ? this.getX() - p_compareTo_1_.getX() : this.getZ() - p_compareTo_1_.getZ();
        } else {
            return this.getY() - p_compareTo_1_.getY();
        }
    }

    /**
     * Gets the X coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the Y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gets the Z coordinate.
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Calculate the cross product of this and the given Vector
     */
    public Vectors3i crossProduct(Vectors3i vec) {
        return new Vectors3i(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public double getDistance(int xIn, int yIn, int zIn) {
        double d0 = (double) (this.getX() - xIn);
        double d1 = (double) (this.getY() - yIn);
        double d2 = (double) (this.getZ() - zIn);
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    /**
     * Calculate squared distance to the given coordinates
     */
    public double distanceSq(double toX, double toY, double toZ) {
        double d0 = (double) this.getX() - toX;
        double d1 = (double) this.getY() - toY;
        double d2 = (double) this.getZ() - toZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    /**
     * Compute square of distance from point x, y, z to center of this Block
     */
    public double distanceSqToCenter(double xIn, double yIn, double zIn) {
        double d0 = (double) this.getX() + 0.5D - xIn;
        double d1 = (double) this.getY() + 0.5D - yIn;
        double d2 = (double) this.getZ() + 0.5D - zIn;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    /**
     * Calculate squared distance to the given Vector
     */
    public double distanceSq(Vectors3i to) {
        return this.distanceSq((double) to.getX(), (double) to.getY(), (double) to.getZ());
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }
}