package team.hdt.blockadia.game_engine.util.toolbox;

import team.hdt.blockadia.game_engine.common.util.math.Maths;

public class Quaternion {

    private double w;
    private double x;
    private double y;
    private double z;

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Quaternion convertFromAxisAngle(double angle, double x, double y, double z) {
        double angleOverTwo = Math.toRadians(angle) / 2d;
        double sinAngleOverTwo = Math.sin(angleOverTwo);
        double newW = Math.cos(angleOverTwo);
        double newX = x * sinAngleOverTwo;
        double newY = y * sinAngleOverTwo;
        double newZ = z * sinAngleOverTwo;
        return new Quaternion(newW, newX, newY, newZ);
    }

    public static Quaternion slerp(Quaternion qA, Quaternion qB, double time) {
        double cosHalfTheta = Maths.dotProductOfQuaternions(qA, qB);
        if (Math.abs(cosHalfTheta) >= 1) {
            return new Quaternion(qA.getW(), qA.getX(), qA.getY(), qA.getZ());
        }
        double halfTheta = Math.acos(cosHalfTheta);
        double sinHalfTheta = Math.sqrt(1 - cosHalfTheta * cosHalfTheta);
        double ratioA = 0.5;
        double ratioB = 0.5;
        if (Math.abs((float) sinHalfTheta) >= 0.001) {
            ratioA = Math.sin((1 - time) * halfTheta) / sinHalfTheta;
            ratioB = Math.sin(time * halfTheta) / sinHalfTheta;
        }
        double newW = qA.getW() * ratioA + qB.getW() * ratioB;
        double newX = qA.getX() * ratioA + qB.getX() * ratioB;
        double newY = qA.getY() * ratioA + qB.getY() * ratioB;
        double newZ = qA.getZ() * ratioA + qB.getZ() * ratioB;
        return new Quaternion(newW, newX, newY, newZ);
    }

    public static Quaternion multiply(Quaternion left, Quaternion right) {
        double w = (left.getW() * right.getW()) - (left.getX() * right.getX())
                - (left.getY() * right.getY()) - (left.getZ() * right.getZ());
        double x = (left.getW() * right.getX()) + (left.getX() * right.getW())
                + (left.getY() * right.getZ()) - (left.getZ() * right.getY());
        double y = (left.getW() * right.getY()) - (left.getX() * right.getZ())
                + (left.getY() * right.getW()) + (left.getZ() * right.getX());
        double z = (left.getW() * right.getZ()) + (left.getX() * right.getY())
                - (left.getY() * right.getX()) + (left.getZ() * right.getW());
        return new Quaternion(w, x, y, z);
    }

    public Quaternion duplicate() {
        return new Quaternion(w, x, y, z);
    }

    public void normalize() {
        double mag = Math.sqrt(w * w + x * x + y * y + z * z);
        w /= mag;
        x /= mag;
        y /= mag;
        z /= mag;
    }

    public double getW() {
        return w;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

}
