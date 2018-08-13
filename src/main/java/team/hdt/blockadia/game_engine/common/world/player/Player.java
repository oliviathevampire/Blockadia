package team.hdt.blockadia.game_engine.common.world.player;

import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.game_engine.client.hud.HUD;
import team.hdt.blockadia.game_engine.common.Main;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.common.util.raytracing.Ray;
import team.hdt.blockadia.game_engine.common.util.raytracing.RayTracer;
import team.hdt.blockadia.game_engine.common.world.Arena;
import team.hdt.blockadia.game_engine.common.world.Entity;
import team.hdt.blockadia.game_engine.common.world.Physics;
import team.hdt.blockadia.game_engine.common.world.block.BlockType;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

public class Player extends Entity {

    private final boolean FLY = false;
    private final int SIZE = 1;
    BendingStyle style;
    BlockType curBlock;
    Vectors3f curBlockVec;
    Projectile projectile;
    private Camera camera;
    private boolean jumped;
    private boolean mouseChanged, keyboardChanged;
    private int lastSearch;
    private int power;
    private int x1 = 0, y1 = 0, z1 = 0;

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
        return null;
    }

    private Vectors3f getBlock(Ray ray) {
        int i = 0;
        while (ray.distance < 10) {
            for (BlockType[][] blockX : arena.blocks) {
                for (BlockType[] blockY : blockX) {
                    for (BlockType block : blockY) {
                        // TODO
                    }
                }
            }
        }
        return null;
    }

    public void processKeyboard(int delta) {

        if (glfwGetMouseButton(Main.display.window, GLFW_KEY_O) == GLFW_PRESS && this.projectile.attached) {
            this.projectile.attached = false;
            this.projectile.momentum = new Vectors3f((float) power / 20, (float) power / 20, (float) power / 20);
        }
        if (glfwGetMouseButton(Main.display.window, GLFW_KEY_P) == GLFW_PRESS) {
////			projectile.attached = true;
            if (!this.projectile.attached) {
                this.projectile = this.style.conjure();
                arena.addProjectile(this.projectile);
            }
        }
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
        if (glfwGetMouseButton(Main.display.window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
            this.projectile.attached = false;
            this.projectile.momentum = RayTracer.getScreenCenterRay().dir;
            this.projectile.speed = (float) power / 20;
        }
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