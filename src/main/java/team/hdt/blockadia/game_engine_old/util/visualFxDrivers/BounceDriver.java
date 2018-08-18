package team.hdt.blockadia.game_engine_old.util.visualFxDrivers;

import team.hdt.blockadia.game_engine_old.common.util.math.Maths;

public class BounceDriver extends ValueDriver {

    private float startValue;
    private float endValue;
    private float peakValue;
    private float max = 0;
    private boolean reachedTarget = false;

    public BounceDriver(float start, float peak, float length) {
        super(length);
        this.startValue = start;
        this.endValue = startValue;
        this.peakValue = peak;
    }

    public BounceDriver(float start, float peak, float end, float length) {
        super(length);
        this.startValue = start;
        this.endValue = end;
        this.peakValue = peak;
    }

    @Override
    protected float calculateValue(float time) {
        if (!reachedTarget && time >= max) {
            max = time;
            if (time < 0.5f) {
                return Maths.cosInterpolate(startValue, peakValue, time * 2f);
            } else {
                return Maths.cosInterpolate(peakValue, endValue, (time - 0.5f) * 2f);
            }
        } else {
            reachedTarget = true;
            return endValue;
        }
    }
}
