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

public class FaceLeft implements Face {
	private static Vector3i vec = new Vector3i(0, -1, 0);
	private static Vector3f normal = new Vector3f(0, -1, 0);
	protected static Vector3i movement = new Vector3i(1, 0, 1);
	protected static Vector3i neighbors[] = new Vector3i[] { new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0),
			new Vector3i(0, 0, 1), new Vector3i(0, 0, -1), };

	@Override
	public String getName() {
		return ("LEFT");
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
		return (Face.get(Face.RIGHT));
	}

	@Override
	public int getID() {
		return (Face.LEFT);
	}

	@Override
	public float getFaceFactor() {
		return (0.70f);
	}

	@Override
	public Vector3i getAllowedTranslation() {
		return (movement);
	}

	@Override
	public Vector3i[] getNeighbors() {
		return (neighbors);
	}
}
