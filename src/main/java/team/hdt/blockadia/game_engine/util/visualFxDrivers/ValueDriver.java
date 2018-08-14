package team.hdt.blockadia.game_engine.util.visualFxDrivers;

public abstract class ValueDriver {

    private float currentTime = 0;
    private float length;
    private boolean oneRep = false;

    public ValueDriver(float length) {
        this.length = length;
    }

    public float update(float delta) {
        this.currentTime += delta;
        if (currentTime >= length) {
            currentTime %= length;
            oneRep = true;
        }
        float time = currentTime / length;
        return calculateValue(time);
    }

    protected abstract float calculateValue(float time);

    public boolean hasCompletedOnePeriod() {
        return oneRep;
    }

}
