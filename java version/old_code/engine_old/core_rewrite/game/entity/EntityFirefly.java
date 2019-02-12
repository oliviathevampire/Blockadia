package team.hdt.blockadia.engine.core_rewrite.game.entity;

import org.joml.Vector2f;
import org.joml.Vector4f;
import team.hdt.blockadia.engine.core_rewrite.gfx.Animation;
import team.hdt.blockadia.engine.core_rewrite.gfx.light.Light;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.EntityRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;

import java.util.Random;

public class EntityFirefly extends EntityMob {

	private Random random;
	private float xa;
	private float ya;

	private Light buttLamp;

	private Animation<Vector2f> dayAnimation;
	private Animation<Vector2f> nightAnimation;

	public EntityFirefly() {
		this(0, 0);
	}

	public EntityFirefly(float x, float y) {
		super(EntityType.NEUTRAL);
		this.setPosition(x, y);
		this.setLastPosition(x, y);
		this.setScale(0.25f);
		this.random = new Random();
		this.buttLamp = new Light(new Vector2f(), new Vector4f(0.86f, 0.97f, 0.05f, 5.0f), 10);

		this.dayAnimation = new Animation<Vector2f>();
		this.dayAnimation.setDelay(75);
		this.dayAnimation.setFrames(new Vector2f(0, 3), new Vector2f(0, 4));
		this.nightAnimation = new Animation<Vector2f>();
		this.nightAnimation.setDelay(75);
		this.nightAnimation.setFrames(new Vector2f(0, 3), new Vector2f(0, 1), new Vector2f(0, 2), new Vector2f(0, 0), new Vector2f(0, 2), new Vector2f(0, 1));
	}

	@Override
	public void update() {
		float speed = 0.25f;
		if (this.getTicksExisted() % (40 + random.nextInt(20)) == 0) {
			xa = random.nextInt(4) - 2;
			ya = random.nextInt(4) - 2;
			if (xa == 0)
				xa = 1;
			if (ya == 0)
				ya = 1;
		}
		this.setX(this.getX() + (xa * speed));
		this.setY(this.getY() + (ya * speed));
		super.update();
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks) {
		this.buttLamp.getPosition().x = this.getRenderX(partialTicks) + 3.75f;
		this.buttLamp.getPosition().y = this.getRenderY(partialTicks) + 6f;
		renderer.renderLights(buttLamp);

		this.dayAnimation.update();
		this.nightAnimation.update();
	}

	@Override
	public Identifier getTexture() {
		return SMALL_ENTITY_TEXTURE_LOCATION;
	}

	@Override
	public Vector2f getTextureOffset() {
		float time = this.getWorld().getTime();
		return time < 0.75f ? nightAnimation.getObject() : dayAnimation.getObject();
	}

	@Override
	public int getTextureWidth() {
		return 16;
	}
}