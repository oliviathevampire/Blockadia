package team.hdt.blockadia.old_engine_code_1.core_rewrite.game.entity;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.util.Identifier;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.Blockadia;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.Display;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.inventory.PlayerInventory;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.renderer.MasterRenderer;

public class EntityPlayer extends EntityLiving {

	private static final Vector2f TEXTURE_COORDS = new Vector2f(0, 0);

	private float dx;
	private float dy;

	private int exp = 0;
	private int level = 0;
	private PlayerInventory inventory;

	public EntityPlayer() {
		this(0, 0);
	}

	public EntityPlayer(float x, float y) {
		super(EntityType.PLAYER);
		this.setPosition(x, y);
		this.setLastPosition(x, y);
		this.setSize(32, 32);
		this.setSpeed(2f);
		this.inventory = new PlayerInventory(45);
	}

	@Override
	public void update() {
		super.update();

		if (!Display.isJoystickPresent()) {
			dx = 0;
			dy = 0;
			if (Display.isKeyPressed(GLFW.GLFW_KEY_W)) {
				dy -= this.getSpeed();
			}

			if (Display.isKeyPressed(GLFW.GLFW_KEY_S)) {
				dy += this.getSpeed();
			}

			if (Display.isKeyPressed(GLFW.GLFW_KEY_A)) {
				dx -= this.getSpeed();
			}

			if (Display.isKeyPressed(GLFW.GLFW_KEY_D)) {
				dx += this.getSpeed();
			}
			this.setX(this.getX() + dx);
			this.setY(this.getY() + dy);
		}

		Blockadia.getInstance().getCamera().move(dx, dy, 0);
		Blockadia.getInstance().getCamera().setPosition(this.getX() - Display.getWidth() / MasterRenderer.scale / 2, this.getY() - Display.getHeight() / MasterRenderer.scale / 2, 0);
	}

	public void onJoystickMoved(double xDirection, double yDirection) {
		this.setX((float) (this.getX() + this.getSpeed() * xDirection));
		this.setY((float) (this.getY() + this.getSpeed() * yDirection));
	}

	@Override
	public float getRenderX(float partialTicks) {
		return this.getX();
	}

	@Override
	public float getRenderY(float partialTicks) {
		return this.getY();
	}

	@Override
	public Identifier getTexture() {
		return ENTITY_TEXTURE_LOCATION;
	}

	@Override
	public Vector2f getTextureOffset() {
		return TEXTURE_COORDS;
	}

	@Override
	public int getTextureWidth() {
		return 8;
	}

	public int getExp() {
		return exp;
	}

	public int getLevel() {
		return level;
	}

	public PlayerInventory getInventory() {
		return inventory;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}