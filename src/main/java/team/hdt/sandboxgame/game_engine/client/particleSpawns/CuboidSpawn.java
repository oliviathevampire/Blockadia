package team.hdt.sandboxgame.game_engine.client.particleSpawns;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors4f;
import team.hdt.sandboxgame.game_engine.util.toolbox.Maths;

public class CuboidSpawn implements ParticleSpawn{
	
	//The position of the particle system is the bottom (-y) upper-left of the cuboid. 
	
	private final float xScale;
	private final float yScale;
	private final float zScale;
	private final float yOffset;
	
	public CuboidSpawn(float xScale, float yScale, float zScale){
		this.xScale = xScale;
		this.yScale = yScale;
		this.zScale = zScale;
		this.yOffset = 0;
	}
	
	public CuboidSpawn(float xScale, float yScale, float zScale, float yOffset){
		this.xScale = xScale;
		this.yScale = yScale;
		this.zScale = zScale;
		this.yOffset = yOffset;
	}

	@Override
	public Vectors4f getBaseSpawnPosition() {
		float xOffset = Maths.RANDOM.nextFloat() * xScale;
		float yOffset = (Maths.RANDOM.nextFloat() * yScale) + this.yOffset;
		float zOffset = Maths.RANDOM.nextFloat() * zScale;
		return new Vectors4f(xOffset, yOffset , zOffset, 1);
	}

}
