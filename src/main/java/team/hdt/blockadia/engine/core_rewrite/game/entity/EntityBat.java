package team.hdt.blockadia.engine.core_rewrite.game.entity;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.gfx.Animation;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.EntityRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.util.AxisAlignedBB;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

import java.util.Random;

public class EntityBat extends EntityMob {

	private Random random;
	private float xa;
	private float ya;

	private Animation<Vector2f> frontAnimation;
	private Animation<Vector2f> sideAnimation;

	public EntityBat() {
		this(0, 0);
	}

	public EntityBat(float x, float y) {
		super(EntityType.ENEMY);
		this.setPosition(x, y);
		this.setLastPosition(x, y);
		this.setScale(0.75f);
		this.random = new Random();

		this.frontAnimation = new Animation<>();
		this.frontAnimation.setDelay(35);
		this.frontAnimation.setFrames(new Vector2f(0, 1), new Vector2f(1, 1), new Vector2f(2, 1), new Vector2f(3, 1), new Vector2f(2, 1) ,new Vector2f(1, 1));
		
		this.sideAnimation = new Animation<>();
		this.sideAnimation.setDelay(35);
		this.sideAnimation.setFrames(new Vector2f(4, 1), new Vector2f(5, 1), new Vector2f(6, 1), new Vector2f(7, 1), new Vector2f(6, 1) ,new Vector2f(5, 1));
	}

	@Override
	public void update() {
		super.update();

		float speed = 0.25f;
		if (this.getTicksExisted() % (40 + random.nextInt(20)) == 0) {
			xa = random.nextInt(4) - 2;
			ya = random.nextInt(4) - 2;
			if(xa == 0)
				xa = 1;
			if(ya == 0)
				ya = 1;
		}
		this.setX(this.getX() + (xa * speed));
		this.setY(this.getY() + (ya * speed));
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks) {
		this.frontAnimation.update();
		this.sideAnimation.update();
	}

	@Override
	public AxisAlignedBB getCollisionBox() {
		return new AxisAlignedBB(this.getX(), this.getY(), 32, 32);
	}

	@Override
	public Identifier getTexture() {
		return ENTITY_TEXTURE_LOCATION;
	}

	@Override
	public Vector2f getTextureOffset() {
		if(this.getVelX() == 0 && this.getVelY() == 0) {
			return this.frontAnimation.getObject();
		}else if(this.getVelX() > 0 || this.getVelX() < 0) {
			return this.sideAnimation.getObject();
		}
		return null;
	}

	@Override
	public int getTextureWidth() {
		return 8;
	}
}