package team.hdt.blockadia.engine.core_rewrite.game.entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.game.GameObject;
import team.hdt.blockadia.engine.core_rewrite.game.world.World;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.EntityRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.util.AxisAlignedBB;

import javax.annotation.Nullable;

public abstract class Entity extends GameObject {

	public static final Identifier ENTITY_TEXTURE_LOCATION = new Identifier("textures/entities.png");
	public static final Identifier SMALL_ENTITY_TEXTURE_LOCATION = new Identifier("textures/entities_small.png");

	private World world;
	private EntityType type;
	private long ticksExisted;
	private float velX; 
	private float velY;

	private Vector3f rotation;
	private float scale;

	private float width;
	private float height;

	private boolean dead;
	private boolean insideFrustum;

	public Entity(EntityType type) {
		this.type = type;
		this.ticksExisted = 0;
		this.rotation = new Vector3f();
		this.scale = 1;
	}

	public void init(World world) {
		this.world = world;
	}

	@Override
	public void update() {
		this.ticksExisted++;
		this.setLastX(this.getX());
		this.setLastY(this.getY());
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks) {
	}

	public void onRemove() {
	}

	@Override
	public AxisAlignedBB getCollisionBox() {
		return new AxisAlignedBB(this.getX(), this.getY(), width, height);
	}

	public int getRenderLayer() {
		return (int) this.getY();
	}

	@Nullable
	public Vector2f getRenderOffset() {
		return null;
	}
	
	public World getWorld() {
		return world;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public long getTicksExisted() {
		return ticksExisted;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public float getScale() {
		return scale;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public boolean isInsideFrustum() {
		return insideFrustum;
	}
	
	protected void setType(EntityType type) {
		this.type = type;
	}
	
	public void setVel(float velX, float velY) {
		this.velX = velX;
		this.velY = velY;
	}
	
	public void setVelX(float velX) {
		this.velX = velX;
	}
	
	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation.set(x, y, z);
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setDead() {
		this.dead = true;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public void setInsideFrustum(boolean insideFrustum) {
		this.insideFrustum = insideFrustum;
	}
}
