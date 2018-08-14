package team.hdt.blockadia.game_engine.common.util.math.interpolation;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class SmoothVector {

    private final float agility;

    private Vectors3f current = new Vectors3f();
    private Vectors3f target = new Vectors3f();

    public SmoothVector(Vectors3f target, float agility) {
        this.target.set(target);
        this.current.set(target);
        this.agility = agility;
    }

    public void update(float delta) {
        Vectors3f diff = Vectors3f.sub(target, current, null);
        float factor = delta * agility;
        if (factor > 1) {
            current.set(target);
        } else {
            diff.scale(factor);
            Vectors3f.add(current, diff, current);
        }
    }

    public void cancelTarget() {
        this.target.set(current);
    }

    public void invertCurrent(float waterHeight) {
        current.y -= 2 * (current.y - waterHeight);
    }

    public Vectors3f getTarget() {
        return target;
    }

    public void setTarget(Vectors3f newTarget) {
        this.target.set(newTarget);
    }

    public void setTarget(float x, float y, float z) {
        this.target.set(x, y, z);
    }

    public void increaseTarget(float dx, float dy, float dz) {
        target.x += dx;
        target.y += dy;
        target.z += dz;
    }

    public void force(Vectors3f newValue) {
        this.current.set(newValue);
        this.target.set(newValue);
    }

    public void force(float x, float y, float z) {
        this.current.set(x, y, z);
        this.target.set(x, y, z);
    }

    public void forceOnlyActualValue(Vectors3f newValue) {
        Vectors3f difference = Vectors3f.sub(target, current, null);
        this.current.set(newValue);
        Vectors3f.add(current, difference, target);
    }

    public boolean reached() {
        float diffSquared = Vectors3f.sub(target, current, null).lengthSquared();
        return diffSquared < 0.00001f;
    }

    public void increaseAll(float dx, float dy, float dz) {
        current.x += dx;
        current.y += dy;
        current.z += dz;
        target.x += dx;
        target.y += dy;
        target.z += dz;
    }

    public Vectors3f get() {
        return current;
    }


}
