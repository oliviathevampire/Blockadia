/*
 * Adam Keenan, 2013
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package team.hdt.sandboxgame.game_engine.common.util.raytracing;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

public class Ray {

    public Vectors3f pos, dir, scaledDir;
    public float distance;
    public float scalar;

    public Ray(Vectors3f pos, Vectors3f dir, float scalar) {
        this.pos = pos;
        this.dir = dir;
        this.scalar = scalar;
        this.scaledDir = scale(dir, scalar);
    }


    public void next() {
        pos = Vectors3f.add(pos, scaledDir, null);
        distance += scalar;
    }

    private Vectors3f scale(Vectors3f vec, float scalar) {
        Vectors3f tmp = new Vectors3f();
        tmp.x = vec.x * scalar;
        tmp.y = vec.y * scalar;
        tmp.z = vec.z * scalar;
        return tmp;
    }

    @Override
    public String toString() {
        return String.format("Ray: Pos = (%s) Dir = (%s)", pos, dir);
    }

}