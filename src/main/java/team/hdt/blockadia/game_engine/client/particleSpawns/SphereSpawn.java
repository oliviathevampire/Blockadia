package team.hdt.blockadia.game_engine.client.particleSpawns;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors4f;
import team.hdt.blockadia.game_engine.util.toolbox.Maths;

public class SphereSpawn implements ParticleSpawn{
	
	private float radius;
	
	public SphereSpawn(float radius){
		this.radius = radius;
	}

	@Override
	public Vectors4f getBaseSpawnPosition() {
		Vectors3f spherePoint = Maths.generateRandomUnitVector();
		
		spherePoint.scale(radius);
		float a = Maths.RANDOM.nextFloat();
		float b = Maths.RANDOM.nextFloat();
		if (a > b) {
			float temp = a;
			a = b;
			b = temp;
		}
		float randX = (float) (b * Math.cos(2 * Math.PI * (a / b)));
		float randY = (float) (b * Math.sin(2 * Math.PI * (a / b)));
		float distance = new Vectors2f(randX, randY).length();
		spherePoint.scale(distance);
		return new Vectors4f(spherePoint.x, spherePoint.y, spherePoint.z, 1f);
	}
	
	

}
