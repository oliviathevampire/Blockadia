package team.hdt.blockadia.game_engine_old.util.visualFxDrivers;

public class SinWaveDriver extends ValueDriver {

    private float min;
    private float amplitude;

    public SinWaveDriver(float min, float max, float length) {
        super(length);
        this.min = min;
        this.amplitude = max - min;
    }

    @Override
    protected float calculateValue(float time) {
        float value = 0.5f + (float) Math.sin(time * Math.PI * 2) * 0.5f;
        return min + (value * amplitude);
    }


}
