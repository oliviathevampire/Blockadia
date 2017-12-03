package net.thegaminghuskymc.sandboxgame.engine.world.entity.ai;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;

/** a simple idle ai, to test the system */
public class EntityAIIdle extends EntityAI {

	public EntityAIIdle(Entity entity) {
		super(entity);
		super.setUpdateTime(1000);
	}

	@Override
	protected void onUpdate(double dt) {

	}

	@Override
	protected void onTimedUpdate() {
		// if (!super.getEntity().isInAir()) {
		// super.getEntity().jump();
		// }
	}

}
