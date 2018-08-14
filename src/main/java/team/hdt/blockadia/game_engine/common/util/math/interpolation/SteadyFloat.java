package team.hdt.blockadia.game_engine.common.util.math.interpolation;

public class SteadyFloat {

    private float maxChangePerSec;

    private float target;
    private float current;
    private boolean reached = true;

    public SteadyFloat(float initialValue, float maxChangePerSec) {
        this.current = initialValue;
        this.target = initialValue;
        this.maxChangePerSec = maxChangePerSec;
    }

    public void setTarget(float target) {
        this.target = target;
        reached = false;
    }

    public boolean isReached() {
        return reached;
    }

    public void setMaxChangePerSec(float maxChange) {
        this.maxChangePerSec = maxChange;
    }

    public float update(float delta) {
        if (reached) {
            return current;
        }
        float difference = target - current;
        float maxAllowedChange = maxChangePerSec * delta;
        if (Math.abs(difference) <= maxAllowedChange) {
            this.reached = true;
            this.current = target;
        } else {
            current += maxAllowedChange * Math.signum(difference);
        }
        return current;
    }

    public float get() {
        return current;
    }


}
