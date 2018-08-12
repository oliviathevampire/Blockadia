package team.hdt.sandboxgame.game_engine.common.world.player;

import jdk.nashorn.internal.ir.Block;
import org.lwjgl.opengl.GL11;
import team.hdt.sandboxgame.game_engine.client.hud.HUD;
import team.hdt.sandboxgame.game_engine.common.Main;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.util.raytracing.Ray;
import team.hdt.sandboxgame.game_engine.common.util.raytracing.RayTracer;
import team.hdt.sandboxgame.game_engine.common.world.Arena;
import team.hdt.sandboxgame.game_engine.common.world.Entity;
import team.hdt.sandboxgame.game_engine.common.world.Physics;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockType;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

public class Player extends Entity {

	BendingStyle style;

	private final boolean FLY = false;

	private Camera camera;

	private boolean jumped;
	private boolean mouseChanged, keyboardChanged;

	BlockType curBlock;
	Vectors3f curBlockVec;
	private int lastSearch;

	Projectile projectile;
	private int power;

	private int x1 = 0, y1 = 0, z1 = 0;
	private final int SIZE = 1;

	public Player(BendingStyle.Element type, Arena arena, int x, int y, int z) {
		super(x + .5f, y, z + .5f);
		this.arena = arena;
		this.camera = new Camera(this, x, y, z);
		this.camera.setup();
		// TODO
//		curBlock = new BlockType(0, 0, 0, null);
		curBlockVec = new Vectors3f();
		this.style = new BendingStyle(this, type);
		this.projectile = this.style.conjure();
		arena.addProjectile(projectile);
	}

	public Camera getCamera() {
		return camera;
	}

	private BlockType getBlockLookedAt() {
		lastSearch++;
		if ((mouseChanged || keyboardChanged) && lastSearch > 5) {
			curBlockVec = getBlock(RayTracer.getScreenCenterRay());
			lastSearch = 0;
		}
		float x, y, z;
		x = curBlockVec.x;
		y = curBlockVec.y;
		z = curBlockVec.z;
		/*camera.drawString(10, 150, String.format("(%s, %s, %s)", x, y, z));
		camera.drawString(10, 170, String.format("(%s, %s, %s)", (int) x, (int) y, (int) z));
		if (x != -1 && y != -1 && z != -1 && arena.inBounds((int) x, (int) y, (int) z)) {
			camera.drawString(400, 170, arena.blocks[(int) x][(int) y][(int) z].toString());
			return arena.blocks[(int) x][(int) y][(int) z];
		}*/
		return new Block(-1, -1, -1, Block.BlockType.OUTLINE);

		}
		return null;
//		return new BlockType(-1, -1, -1, BlockType.BlockType.OUTLINE);
	}

	private Vectors3f getBlock(Ray ray) {
		int i = 0;
		lbl: while (ray.distance < 10) {
			for (BlockType[][] blockX : arena.blocks) {
				for (BlockType[] blockY : blockX) {
					for (BlockType block : blockY) {
						// TODO
//						if (!block.isWalkThroughable())
//							if (block.contains(ray.pos)) {
//								i++;
//								break lbl;
//							} else if (!arena.contains(ray.pos)) {
//								ray.pos.set(-1, -1, -1);
//								break lbl;
//							}
					}
				}
			}
			ray.next();
		}
		if (i > 0) {
			x1 = (int) ray.pos.x;
			y1 = (int) ray.pos.y;
			z1 = (int) ray.pos.z;
		} else {
			x1 = 0;
			y1 = 0;
			z1 = 0;
			ray.pos.set(-1, -1, -1);
		}
		return ray.pos;
	}

	public void processKeyboard(int delta) {

		if (glfwGetMouseButton(Main.display.window, GLFW_KEY_O) == GLFW_PRESS && this.projectile.attached) {
			this.projectile.attached = false;
			this.projectile.momentum = new Vectors3f((float)power / 20, (float)power / 20, (float)power / 20);
		}
		if (glfwGetMouseButton(Main.display.window, GLFW_KEY_P) == GLFW_PRESS) {
//			projectile.attached = true;
			if (!this.projectile.attached) {
				this.projectile = this.style.conjure();
				arena.addProjectile(this.projectile);
			}
		}
		/*while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN)
					power -= power == 0 ? 0 : 1;
				if (Keyboard.getEventKey() == Keyboard.KEY_UP)
					power += power == 10 ? 0 : 1;
			}
		}
		
		boolean keyUp, keyDown, keyRight, keyLeft, keySpace, keyShift;
		
		keyUp = Keyboard.isKeyDown(Keyboard.KEY_W);
		keyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
		keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
		keyRight = Keyboard.isKeyDown(Keyboard.KEY_D);
		keySpace = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		keyShift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		
		float dx = 0, dy, dz = 0;
		float amount = delta * .003f;
		dx += keyRight ? amount : 0;
		dx += keyLeft ? -amount : 0;
		dz += keyUp ? -amount : 0;
		dz += keyDown ? amount : 0;
		if (!FLY) {
			if (keySpace && !jumped) {
				jumped = true;
				System.out.println("Pressed jumped");
				fallSpeed = -.15f;
			}
			if (Physics.gravity(this)) {
				keyboardChanged = true;
				fallSpeed += fallSpeed > 1.5f ? 0 : .01f;
				jumped = true;
			} else {
				keyboardChanged = false;
				fallSpeed = 0;
				jumped = false;
			}
			move(dx, dz);
		} else {
			if (keySpace)
				this.y += .1f;
			if (keyShift)
				this.y -= .1f;
			this.x += dx;
			this.y += dy;
			this.z += dz;
			camera.moveFromLook(dx, dy, dz);
		}*/
	}

	private void move(float dx, float dz) {
		if (dx == 0 && dz == 0) {
			keyboardChanged = false;
			return;
		}
		keyboardChanged = true;
		Physics.moveWithCollisions(this, dx, dz, null);
	}

	public void processMouse() {
		if (glfwGetMouseButton(Main.display.window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS)
//			curBlock.move(0, -.1f, 0);
		if (glfwGetMouseButton(Main.display.window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
			this.projectile.attached = false;
			this.projectile.momentum = RayTracer.getScreenCenterRay().dir;
			this.projectile.speed = (float)power / 20;
		}
		/*if (Mouse.hasWheel()) {
			int wheel = Mouse.getDWheel();
			if (wheel != 0)
//			System.out.println(wheel);
			if (wheel < 0)
				power -= power == 0 ? 0 : 1;
			if (wheel > 0)
				power += power == 10 ? 0 : 1;
		}*/
		mouseChanged = camera.processMouse(.75f, 90, -80);
		this.yaw = camera.yaw;
	}

	@Override
	public void update() {
		camera.x = this.x;
		camera.y = this.y + 1.62f;
		camera.z = this.z;
		camera.update();

	}

	@Override
	public void render() {
		curBlock = getBlockLookedAt();
		GL11.glColor4f(1, 1, 1, .5f);
		for (int row = 0; row < SIZE; row++)
			for (int col = 0; col < SIZE; col++) {
				glBegin(GL11.GL_QUADS);
				{
					GL11.glVertex3f(x1, y1 + 1, col + z1);
					GL11.glVertex3f(x1 + 1, y1 + 1, col + z1);
					GL11.glVertex3f(x1 + 1, y1 + 1, col + z1 + 1);
					GL11.glVertex3f(x1, y1 + 1, col + z1 + 1);
				}
				glEnd();
			}
		GL11.glColor3f(1, 1, 1);
		/*camera.drawDebug();
		camera.drawString(100, 300, String.format("%f, %f, %f", projectile.x, projectile.y, projectile.z));
		camera.drawString(Display.getWidth() - 200, Display.getHeight() - 20, String.format("Power: %s", power));*/
		HUD.drawCrosshairs();
	}

	@Override
	public String toString() {
		return String.format("(%s, %s, %s)", x, y, z);
	}

}
