package team.hdt.blockadia.old_engine_code_1.core_rewrite.game.entity;

import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.renderer.EntityRenderer;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.renderer.MasterRenderer;

public abstract class EntityMob extends EntityLiving {

	public EntityMob(EntityType type) {
		super(type);
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks) {
		// if (this.getY() < Blockadia.HEIGHT - 32 && this.getY() > 0 - 32 && this.getX() < Blockadia.WIDTH && this.getX() > 0 - 32) {
		// g.setColor(Color.RED);
		// g.fillRect(this.getX(), this.getY(), 16, 16);
		// }
	}
}
