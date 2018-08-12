package team.hdt.sandboxgame.game_engine.common.world.player;

import team.hdt.sandboxgame.game_engine.client.rendering.GLShapes;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.util.raytracing.RayTracer;
import team.hdt.sandboxgame.game_engine.common.world.Entity;
import team.hdt.sandboxgame.game_engine.common.world.Physics;
import team.hdt.sandboxgame.game_engine.common.world.block.Block;

public class Projectile extends Entity {
	
	Player player;
	BendingStyle.Element type;
	
	boolean attached;
	
	public Projectile(Player player, BendingStyle.Element type) {
		super(player.x, player.y + 1.5f, player.z);
		this.player = player;
		this.arena = this.player.arena;
		this.type = type;
		this.attached = true;
	}
	
	@Override
	public void update() {
		if (attached) {
			fallSpeed = 0;
			Vectors3f dir = RayTracer.getScreenCenterRay().dir;
			this.x = this.player.x + dir.x;
			this.y = this.player.y + 1.5f + dir.y;
			this.z = this.player.z + dir.z;
		} else {
			fallSpeed += .01f;
			Physics.gravity(this);
			Physics.moveEntityMomentum(this, player);
		}
	}
	
	@Override
	public void render() {
		GLShapes.drawCube(Block.BlockType.FIRE, x, y, z);
	}
	
}
