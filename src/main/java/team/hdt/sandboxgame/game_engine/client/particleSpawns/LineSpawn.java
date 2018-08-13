package team.hdt.sandboxgame.game_engine.client.particleSpawns;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors4f;

import java.util.Random;

public class LineSpawn implements ParticleSpawn {
	
	private float length;
	private Vectors3f axis;
	private Random random = new Random();
	
	public LineSpawn(float length, Vectors3f axis){
		this.length = length;
		this.axis = (Vectors3f) axis.normalise();
	}

	@Override
	public Vectors4f getBaseSpawnPosition() {
		float actualLength = length;
		Vectors3f actualAxis = new Vectors3f(axis.x * actualLength, axis.y * actualLength, axis.z * actualLength);
		actualAxis.scale(random.nextFloat()-0.5f);
		return new Vectors4f(actualAxis.x, actualAxis.y, actualAxis.z, 1f);
	}
	
	

}
