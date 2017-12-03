/**
**	This file is part of the project https://github.com/toss-dev/VoxelEngine
**
**	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
**
**	PEREIRA Romain
**                                       4-----7          
**                                      /|    /|
**                                     0-----3 |
**                                     | 5___|_6
**                                     |/    | /
**                                     1-----2
*/

package net.thegaminghuskymc.sandboxgame.engine.faces;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public interface Face {

	int NULL = -1;

	/** z - */
	int LEFT = 0;

	/** z + */
	int RIGHT = 1;

	/** y + */
	int TOP = 2;

	/** y - */
	int BOT = 3;

	/** x - */
	int FRONT = 4;

	/** x + */
	int BACK = 5;

	Face[] faces = { new FaceLeft(), new FaceRight(), new FaceTop(), new FaceBot(), new FaceFront(),
			new FaceBack(), };

	Face F_LEFT = Face.get(LEFT);
	Face F_RIGHT = Face.get(RIGHT);
	Face F_TOP = Face.get(TOP);
	Face F_BOT = Face.get(BOT);
	Face F_FRONT = Face.get(FRONT);
	Face F_BACK = Face.get(BACK);

	static Face get(int index) {
		return (faces[index]);
	}

	static Face[] values() {
		return (faces);
	}

	String getName();

	public Vector3f getNormal();

	public Vector3i getVector();

	public Face getOpposite();

	public int getID();

	public abstract float getFaceFactor();

	public static Face fromVec(Vector3f vec) {
		if (vec.x > 0 && vec.x > vec.y && vec.x > vec.z) {
			return (Face.get(Face.BACK));
		}
		if (vec.x < 0 && vec.x < vec.y && vec.x < vec.z) {
			return (Face.get(Face.FRONT));
		}
		if (vec.y > 0 && vec.y > vec.x && vec.y > vec.z) {
			return (Face.get(Face.TOP));
		}
		if (vec.y < 0 && vec.y < vec.x && vec.y < vec.z) {
			return (Face.get(Face.BOT));
		}
		if (vec.z > 0 && vec.z > vec.x && vec.z > vec.y) {
			return (Face.get(Face.RIGHT));
		}
		return (Face.get(Face.LEFT));
	}

	public static Face fromVec(Vector3i vec) {
		if (vec.x > 0 && vec.x > vec.y && vec.x > vec.z) {
			return (Face.get(Face.BACK));
		}
		if (vec.x < 0 && vec.x < vec.y && vec.x < vec.z) {
			return (Face.get(Face.FRONT));
		}
		if (vec.y > 0 && vec.y > vec.x && vec.y > vec.z) {
			return (Face.get(Face.TOP));
		}
		if (vec.y < 0 && vec.y < vec.x && vec.y < vec.z) {
			return (Face.get(Face.BOT));
		}
		if (vec.z > 0 && vec.z > vec.x && vec.z > vec.y) {
			return (Face.get(Face.RIGHT));
		}
		return (Face.get(Face.LEFT));
	}

	public Vector3i getAllowedTranslation();
}
