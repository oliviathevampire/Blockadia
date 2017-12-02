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

import com.grillecube.common.maths.Vector3f;
import com.grillecube.common.maths.Vector3i;

public class FaceRight implements Face {
	private static Vector3i vec = new Vector3i(0, 0, 1);
	private static Vector3f normal = new Vector3f(0, 0, 1);
	private static Vector3i movement = new Vector3i(1, 1, 0);

	@Override
	public String getName() {
		return ("RIGHT");
	}

	@Override
	public Vector3f getNormal() {
		return (normal);
	}

	@Override
	public Vector3i getVector() {
		return (vec);
	}

	@Override
	public Face getOpposite() {
		return (Face.get(Face.LEFT));
	}

	@Override
	public int getID() {
		return (Face.RIGHT);
	}

	@Override
	public float getFaceFactor() {
		return (0.70f);
	}

	@Override
	public Vector3i getAllowedTranslation() {
		return (movement);
	}
}