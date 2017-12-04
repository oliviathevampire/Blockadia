package net.thegaminghuskymc.sandboxgame.engine.faces;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;

public interface Face {

	int NULL = -1;

	int LEFT = 0;

	int RIGHT = 1;

	int TOP = 2;

	int BOT = 3;

	int FRONT = 4;

	int BACK = 5;

	Face[] faces = { new FaceLeft(), new FaceRight(), new FaceTop(), new FaceBot(), new FaceFront(),
			new FaceBack(), };

	Face F_LEFT = Face.get(LEFT);
	Face F_RIGHT = Face.get(RIGHT);
	Face F_TOP = Face.get(TOP);
	Face F_BOT = Face.get(BOT);
	Face F_FRONT = Face.get(FRONT);

	static Face get(int index) {
		return (faces[index]);
	}

	static Face[] values() {
		return (faces);
	}

	String getName();

	Vector3f getNormal();

	Vector3i getVector();

	Face getOpposite();

	int getID();

	float getFaceFactor();

	static Face fromVec(Vector3i vec) {
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

	Vector3i getAllowedTranslation();
}
