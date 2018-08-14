package team.hdt.blockadia.game_engine.common.util.math.interpolation;

public class RampFloat {

    private final float max;
    private final float min;
    private final float changePerSec;

    private float value = 0;

    public RampFloat(float min, float max, float changePerSec) {
        this.min = min;
        this.max = max;
        this.changePerSec = changePerSec;
    }

    public float get() {
        return value;
    }

    public void ramp(float delta) {
        value += changePerSec * delta;
        value = Math.min(value, max);
    }

    public void rampDown(float delta) {
        value -= changePerSec * delta;
        value = Math.max(value, min);
    }

}
