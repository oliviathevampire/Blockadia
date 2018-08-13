package team.hdt.sandboxgame.game_engine.client.particleSpawns;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors4f;

public class PointSpawn implements ParticleSpawn{
	
	public PointSpawn(){

	}

	@Override
	public Vectors4f getBaseSpawnPosition() {
		return new Vectors4f(0f,0f, 0f, 1f);
	}

}
