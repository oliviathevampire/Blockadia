package team.hdt.blockadia.engine_rewrite.core.entitys;

import ga.pheonix.utillib.utils.anouncments.Nonnull;
import ga.pheonix.utillib.utils.anouncments.Nullable;
import ga.pheonix.utillib.utils.vectors.Vectors3f;
import team.hdt.blockadia.engine_rewrite.core.utils.AxisAlignedBB3D;

public abstract class Entity {
    public static boolean fly = false;
    @Nonnull
    public static String id;
    @Nonnull
    private static String name;
    private static float scale;
    public static Vectors3f postion;
    public static Vectors3f rotation;
    @Nullable
    private static float runspeed;
    @Nullable
    private static float walkspeed;
    private static AxisAlignedBB3D zero_hitbox = new AxisAlignedBB3D(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public boolean isDead;

    public static AxisAlignedBB3D getZero_hitbox() {
        return zero_hitbox;
    }

    public static void setZero_hitbox(AxisAlignedBB3D zero_hitbox) {
        Entity.zero_hitbox = zero_hitbox;
    }

    public String getID() {
        return id;
    }

    public void setID(String i) {
        id = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float v) {
        scale = v;
    }

    public void setPosition(float v, float v1, float v2) {
        postion.x = v;
        postion.y = v1;
        postion.z = v2;
    }

    public Vectors3f getPosition() {
        return postion;
    }

    public void setPosition(Vectors3f v) {
        postion = v;
    }

    public void setRotation(float v, float v1, float v2) {
        rotation.x = v;
        rotation.y = v1;
        rotation.z = v2;
    }

    public Vectors3f getRotation() {
        return rotation;
    }

    public void setRotation(Vectors3f vectors3f) {
        rotation = vectors3f;
    }

    public Entity getEntity() {
        return this;
    }

    public float getWalkSpeed() {
        return walkspeed;
    }

    public void setWalkSpeed(float v) {
        walkspeed = v;
    }

    public float getRunSpeed() {
        return runspeed;
    }

    public void setRunSpeed(float v) {
        runspeed = v;
    }

    public boolean canFly(boolean b) {
        return fly;
    }

    /**
     * Resets the entity's position to the center (planar) and bottom (vertical) points of its bounding box.
     */
    public void resetPositionToBB() {
        AxisAlignedBB3D axisalignedbb = this.getEntityBoundingBox();
        postion.x = ((float) axisalignedbb.minX + (float) axisalignedbb.maxX) / 2.0F;
        postion.y = (float) axisalignedbb.minY;
        postion.y = ((float) axisalignedbb.minZ + (float) axisalignedbb.maxZ) / 2.0F;
    }

    public boolean hasNoGravity() {
        return !fly;
    }

    @Nullable

    /**
     * Returns the collision bounding box for this entity
     */
    public AxisAlignedBB3D getCollisionBoundingBox() {
        return null;
    }

    /**
     * Returns the squared distance to the entity.
     */
    public double getDistanceSqToEntity(Entity entityIn) {
        double d0 = postion.x - postion.x;
        double d1 = postion.y - postion.y;
        double d2 = postion.z - postion.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityIn) {
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return false;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed() {
        return false;
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB3D getCollisionBox(Entity entityIn) {
        return null;
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public Float getYOffset() {
        return 0.0F;
    }

    /**
     * Set the position and rotation values directly without any clamping.
     */
    public void setPositionAndRotationDirect(float px, float py, float pz, float rx, float ry, float rz) {
        this.setPosition(px, py, pz);
        this.setRotation(rx, ry, rz);
    }

    public AxisAlignedBB3D getEntityBoundingBox() {
        return zero_hitbox;
    }

    public void setEntityBoundingBox(AxisAlignedBB3D bb) {
        zero_hitbox = bb;
    }

    public AxisAlignedBB3D getRenderBoundingBox() {
        return this.getEntityBoundingBox();
    }

    public float getEyeHeight() {
        return ((scale / 2) + (scale / 4));
    }

    @Nullable

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    public Entity getControllingPassenger() {
        return null;
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem() {
        return true;
    }

    public boolean canBeHurt() {
        return false;
    }

    public boolean canBeWornDown() {
        return false;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    protected abstract void entityInit();


}
