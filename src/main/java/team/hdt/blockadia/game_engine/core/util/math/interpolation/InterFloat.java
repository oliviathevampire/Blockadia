package team.hdt.blockadia.game_engine.core.util.math.interpolation;

import team.hdt.blockadia.game_engine.core.util.math.Maths;

public class InterFloat {

    private float changePerSec;

    private float start;
    private float finish;
    private float totalRequiredTime;

    private float time = 0;

    public InterFloat(float changePerSec) {
        this.changePerSec = changePerSec;
    }

    public InterFloat() {
    }

    public void setSlide(float start, float finish) {
        this.start = start;
        this.finish = finish;
        this.totalRequiredTime = Math.abs(finish - start) / changePerSec;
        time = 0;
    }

    public void setSlideWithChange(float start, float finish, float changePerSec) {
        this.changePerSec = changePerSec;
        this.start = start;
        this.finish = finish;
        this.totalRequiredTime = Math.abs(finish - start) / changePerSec;
        time = 0;
    }

    public void setSlideWithSetTime(float start, float finish, float timeTake) {
        this.totalRequiredTime = timeTake;
        this.start = start;
        this.finish = finish;
        this.changePerSec = Math.abs(finish - start) / totalRequiredTime;
        time = 0;
    }

    public boolean isReached() {
        return time >= totalRequiredTime;
    }

    public float update(float delta) {
        time += delta;
        if (isReached()) {
            return finish;
        }
        float blend = time / totalRequiredTime;
        blend = Math.min(1, blend);
        return Maths.interpolate(start, finish, blend);
    }

}
