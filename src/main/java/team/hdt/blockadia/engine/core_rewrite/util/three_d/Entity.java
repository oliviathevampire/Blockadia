package team.hdt.blockadia.engine.core_rewrite.util.three_d;

import ga.pheonix.utillib.utils.vectors.Vectors3f;

public class Entity{
    public static boolean fly = false;
    public static int id;
    private static String name;
    private static float scale;
    private static Vectors3f postion;
    private static Vectors3f rotation;
    private static float runspeed;
    private static float walkspeed;

    public void setID(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public void setScale(float v) {
        scale = v;
    }

    public float getScale() {
        return scale;
    }

    public void setposition(float v, float v1, float v2) {
        postion.x = v;
        postion.y = v1;
        postion.z = v2;
    }

    public void setposition(Vectors3f vectors3f) {
        postion = vectors3f;
    }

    public Vectors3f getPosition() {
        return postion;
    }

    public void setRotation(float v, float v1, float v2) {
        rotation.x = v;
        rotation.y = v1;
        rotation.z = v2;
    }

    public void setRotation(Vectors3f vectors3f) {
        rotation = vectors3f;
    }

    public Vectors3f getRotation() {
        return rotation;
    }

    public Entity getEntity() {
        return this;
    }

    public void setWalkSpeed(float v) {
        walkspeed = v;
    }

    public float getWalkSpeed() {
        return walkspeed;
    }

    public void setRunSpeed(float v) {
        runspeed = v;
    }

    public float getRunSpeed() {
        return runspeed;
    }
    public boolean canFly(boolean b) {
        return fly;
    }
}
