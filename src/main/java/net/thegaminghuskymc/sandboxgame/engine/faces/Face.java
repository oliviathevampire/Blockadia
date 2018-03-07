/**
 * *	This file is part of the project https://github.com/toss-dev/VoxelEngine
 * *
 * *	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
 * *
 * *	PEREIRA Romain
 * *                                       4-----7
 * *                                      /|    /|
 * *                                     0-----3 |
 * *                                     | 5___|_6
 * *                                     |/    | /
 * *                                     1-----2
 */

package net.thegaminghuskymc.sandboxgame.engine.faces;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public interface Face {

    public static final int NULL = -1;

    /** z + */
    public static final int TOP = 0;

    /** z - */
    public static final int BOT = 1;

    /** y + */
    public static final int RIGHT = 2;

    /** y - */
    public static final int LEFT = 3;

    /** x + */
    public static final int FRONT = 4;

    /** x - */
    public static final int BACK = 5;

    public static final Face[] faces = new Face[]{new FaceTop(), new FaceBot(), new FaceRight(), new FaceLeft(),
            new FaceFront(), new FaceBack()

    };

    public static final Face F_TOP = Face.get(TOP);
    public static final Face F_BOT = Face.get(BOT);
    public static final Face F_RIGHT = Face.get(RIGHT);
    public static final Face F_LEFT = Face.get(LEFT);
    public static final Face F_FRONT = Face.get(FRONT);
    public static final Face F_BACK = Face.get(BACK);

    public static Face get(int index) {
        return (faces[index]);
    }

    public static Face[] values() {
        return (faces);
    }

    public static Face fromVec(Vector3f vec) {
        if (vec.x > 0 && vec.x > vec.y && vec.x > vec.z) {
            return (Face.get(Face.FRONT));
        }
        if (vec.x < 0 && vec.x < vec.y && vec.x < vec.z) {
            return (Face.get(Face.BACK));
        }
        if (vec.y > 0 && vec.y > vec.x && vec.y > vec.z) {
            return (Face.get(Face.RIGHT));
        }
        if (vec.y < 0 && vec.y < vec.x && vec.y < vec.z) {
            return (Face.get(Face.LEFT));
        }
        if (vec.z > 0 && vec.z > vec.x && vec.z > vec.y) {
            return (Face.get(Face.TOP));
        }
        return (Face.get(Face.BOT));
    }

    public static Face fromVec(Vector3i vec) {
        if (vec.x > 0 && vec.x > vec.y && vec.x > vec.z) {
            return (Face.get(Face.FRONT));
        }
        if (vec.x < 0 && vec.x < vec.y && vec.x < vec.z) {
            return (Face.get(Face.BACK));
        }
        if (vec.y > 0 && vec.y > vec.x && vec.y > vec.z) {
            return (Face.get(Face.RIGHT));
        }
        if (vec.y < 0 && vec.y < vec.x && vec.y < vec.z) {
            return (Face.get(Face.LEFT));
        }
        if (vec.z > 0 && vec.z > vec.x && vec.z > vec.y) {
            return (Face.get(Face.TOP));
        }
        return (Face.get(Face.BOT));
    }

    public String getName();

    public Vector3f getNormal();

    public Vector3i getVector();

    public Face getOpposite();

    public int getID();

    public abstract float getFaceFactor();

    public Vector3i getAllowedTranslation();

    public Vector3i[] getNeighbors();
}
