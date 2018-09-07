package team.hdt.blockadia.game_engine.core.util;

import team.hdt.blockadia.game_engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;

public class Ray {
    public Vectors3f origin;
    public Vectors3f direction;
    public Vectors3f inv_direction;
    public int[] sign;

    public Ray(Matrix4fs proj, Matrix4fs view, Vectors2f mouse, int width, int height) {
        Vectors3f v = new Vectors3f();
        v.x = (((2.0f * mouse.x) / width) - 1) / proj.m00;
        v.y = -(((2.0f * mouse.y) / height) - 1) / proj.m11;
        v.z = 1.0f;

        Matrix4fs invert_view = Matrix4fs.invert(view, null);

        Vectors3f ray_direction = new Vectors3f();
        ray_direction.x = v.x * invert_view.m00 + v.y * invert_view.m10 + v.z * invert_view.m20;
        ray_direction.y = v.x * invert_view.m01 + v.y * invert_view.m11 + v.z * invert_view.m21;
        ray_direction.z = v.x * invert_view.m02 + v.y * invert_view.m12 + v.z * invert_view.m22;

        Vectors3f ray_origin = new Vectors3f(invert_view.m30, invert_view.m31, invert_view.m32);

        init(ray_origin, ray_direction);
    }

    public Ray(Vectors3f origin, Vectors3f direction) {
        init(origin, direction);
    }

    private void init(Vectors3f origin, Vectors3f direction) {
        this.origin = new Vectors3f(origin.x, origin.y, origin.z);
        this.direction = new Vectors3f(direction.x, direction.y, direction.z);
        this.inv_direction = new Vectors3f(1 / direction.x, 1 / direction.y, 1 / direction.z);

        this.sign = new int[3];
        this.sign[0] = (inv_direction.x < 0) ? 1 : 0;
        this.sign[1] = (inv_direction.y < 0) ? 1 : 0;
        this.sign[2] = (inv_direction.z < 0) ? 1 : 0;
    }

    public boolean intersect(AABB box, float t0, float t1) {
        float tmin = (box.corners[sign[0]].x - origin.x) * inv_direction.x;
        float tmax = (box.corners[1 - sign[0]].x - origin.x) * inv_direction.x;
        float tymin = (box.corners[sign[1]].y - origin.y) * inv_direction.y;
        float tymax = (box.corners[1 - sign[1]].y - origin.y) * inv_direction.y;
        if ((tmin > tymax) || (tymin > tmax)) {
            return false;
        }
        if (tymin > tmin) {
            tmin = tymin;
        }
        if (tymax < tmax) {
            tmax = tymax;
        }
        float tzmin = (box.corners[sign[2]].z - origin.z) * inv_direction.z;
        float tzmax = (box.corners[1 - sign[2]].z - origin.z) * inv_direction.z;
        if (tmin > tzmax || tzmin > tmax) {
            return false;
        }
        if (tzmin > tmin) {
            tmin = tzmin;
        }
        if (tzmax < tmax) {
            tmax = tzmax;
        }
        return tmin < t1 && tmax > t0;
    }

    public String toString() {
        return "origin: " + origin.x + ", " + origin.y + ", " + origin.z + ";\n direction: " + direction.x + ", " + direction.y + ", " + direction.z + "";
    }
}
