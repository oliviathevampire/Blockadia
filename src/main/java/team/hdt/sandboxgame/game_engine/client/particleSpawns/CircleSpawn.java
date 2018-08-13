package team.hdt.sandboxgame.game_engine.client.particleSpawns;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors4f;
import team.hdt.sandboxgame.game_engine.util.toolbox.Maths;

public class CircleSpawn implements ParticleSpawn{
	
	private Vectors3f heading;
	private float radius;
	
	public CircleSpawn(Vectors3f heading, float radius){
		this.heading = (Vectors3f) heading.normalise();
		this.radius = radius;
	}

	@Override
	public Vectors4f getBaseSpawnPosition() {
		Vectors3f point = Maths.randomPointOnCircle(heading, radius);
		return new Vectors4f(point.x, point.y, point.z, 1f);
	}

}
