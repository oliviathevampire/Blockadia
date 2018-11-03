package team.hdt.blockadia.engine.core_rewrite.game.entity.item;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.game.entity.Entity;
import team.hdt.blockadia.engine.core_rewrite.game.entity.EntityPlayer;
import team.hdt.blockadia.engine.core_rewrite.game.entity.EntityType;
import team.hdt.blockadia.engine.core_rewrite.game.item.ItemStack;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.util.AxisAlignedBB;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

public class EntityItem extends Entity {

	private ItemStack stack;

	public EntityItem(ItemStack stack) {
		this(stack, 0, 0);
	}

	public EntityItem(ItemStack stack, float x, float y) {
		super(EntityType.NEUTRAL);
		this.setPosition(x, y);
		this.setLastPosition(x, y);
		this.setScale(1f / MasterRenderer.scale);
		this.setSize(4, 4);
		this.stack = stack;
	}

	@Override
	public void update() {
		super.update();
		EntityPlayer player = this.getWorld().getPlayer();
		if(player != null) {
			AxisAlignedBB playerBox = player.getCollisionBox();
			if(playerBox.intersects(this.getCollisionBox())) {
				ItemStack remaining = player.getInventory().addStackToInventory(this.stack);
				if(remaining.isEmpty()) {
					this.setDead();
				}
				this.stack = remaining;
			}
		}
	}

	@Override
	public Vector2f getRenderOffset() {
		return new Vector2f();
	}

	@Override
	public Identifier getTexture() {
		return this.stack.getItem().getTexture();
	}

	@Override
	public Vector2f getTextureOffset() {
		return this.stack.getItem().getTextureCoords(0);
	}

	@Override
	public int getTextureWidth() {
		return this.stack.getItem().getTextureWidth();
	}
}