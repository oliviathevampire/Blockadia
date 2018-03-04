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

package net.thegaminghuskymc.sandboxgame.game.client.renderer.particles;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;

public class ParticleCube extends WorldObjectParticle {
	protected Matrix4f transfMatrix;

	public ParticleCube(int health) {
		super(health);
		this.transfMatrix = new Matrix4f();
	}

	public ParticleCube() {
		this(1000);
	}

	public final Matrix4f getTransfMatrix() {
		return (this.transfMatrix);
	}

	@Override
	protected void onUpdate(double dt) {
		this.calculateTransformationMatrix();
	}

	protected void calculateTransformationMatrix() {
		Matrix4f.createTransformationMatrix(this.transfMatrix, this.getPosition(), this.getRotation(), this.getSize());
	}
}
