package net.thegaminghuskymc.sandboxgame.engine.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.world.World;

/** represent static entities (chair...) */
public abstract class WorldEntityStatic extends WorldEntity {

	public WorldEntityStatic(World world) {
		super(world);
	}

	@Override
	protected void onUpdate(double dt) {
	}
}
