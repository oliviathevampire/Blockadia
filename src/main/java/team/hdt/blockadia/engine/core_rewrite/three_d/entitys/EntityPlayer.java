package team.hdt.blockadia.engine.core_rewrite.three_d.entitys;

import ga.pheonix.utillib.utils.vectors.Vectors3f;
import org.lwjgl.glfw.GLFW;
import team.hdt.blockadia.engine.core_rewrite.Display;
import team.hdt.blockadia.engine.core_rewrite.game.inventory.PlayerInventory;
import team.hdt.blockadia.engine.core_rewrite.three_d.AxisAlignedBB3D;

public class EntityPlayer extends LivingEntity {
    public int exp = 0;
    public int level = 0;
    private Vectors3f postion;
    private Vectors3f rotation;
    public static int health;
    public static float attackdamage;
    public static float defence;                                      //            min                        max
    private static AxisAlignedBB3D player_hitbox = new AxisAlignedBB3D(0.0D, 0.0D, 0.0D, 1.0D, 2.5D, 07.5D);
    private PlayerInventory inventory;

    public EntityPlayer() {
        health = 20;
        attackdamage = 2.1F;
        /**
         * this is only ro go from 1 max to 0 while out armor applied
         */
        defence = 0.2F;
        this.setWalkSpeed(0.5F);
        this.setRunSpeed(1.0F);
        this.setEntityBoundingBox(player_hitbox);
    }

    protected void updateAITasks() {
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public void move() {
        if (!Display.isJoystickPresent()) {
            this.setPosition(0, 0, 0);

            if (Display.isKeyPressed(GLFW.GLFW_KEY_W)) {
                this.postion.z += 1.0F;
            }
            if (Display.isKeyPressed(GLFW.GLFW_KEY_S)) {
                this.postion.z -= 1.0F;
            }
            if (Display.isKeyPressed(GLFW.GLFW_KEY_A)) {
                this.postion.x -= 1.0F;
            }
            if (Display.isKeyPressed(GLFW.GLFW_KEY_D)) {
                this.postion.x += 1.0F;
            }
            if (Display.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                this.postion.y += 1.0F;
            }
        }
    }

    //TODO:finish creating player
}
