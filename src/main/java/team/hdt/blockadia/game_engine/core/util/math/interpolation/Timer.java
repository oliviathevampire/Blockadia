package team.hdt.blockadia.game_engine.core.util.math.interpolation;

import team.hdt.blockadia.game_engine_old.client.ClientMain;
import team.hdt.blockadia.game_engine.core.util.math.Maths;

public class Timer {

    private final float minTime;
    private final float maxTime;
    private final boolean randomTime;
    private final boolean looping;
    private final boolean gameTime;
    private float totalTime;
    private boolean started = false;
    private float time = 0;

    private Timer(float totalTime, boolean looping, boolean gameTime) {
        this.totalTime = totalTime;
        this.looping = looping;
        this.gameTime = gameTime;
        this.randomTime = false;
        this.minTime = 0;
        this.maxTime = 0;
    }

    private Timer(float minTime, float maxTime, boolean gameTime) {
        this.totalTime = Maths.randomNumberBetween(minTime, maxTime);
        this.looping = true;
        this.gameTime = gameTime;
        this.randomTime = true;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public static Timer createLoopingTimer(float minTime, float maxTime, boolean gameTime) {
        return new Timer(minTime, maxTime, gameTime);
    }

    public static Timer createLoopingTimer(float time, boolean gameTime) {
        return new Timer(time, true, gameTime);
    }

    public static Timer createOneOffTimer(float time, boolean gameTime) {
        return new Timer(time, false, gameTime);
    }

    public Timer start() {
        time = 0;
        started = true;
        return this;
    }

    public Timer reset() {
        time = 0;
        return this;
    }

    public void resetTo(float maxTime) {
        time = 0;
        this.totalTime = maxTime;
    }

    public void stop() {
        time = 0;
        started = false;
    }

    public boolean check() {
        if (!looping && !started) {
            return false;
        }
        float timePassed = gameTime ? ClientMain.getGameSeconds() : ClientMain.getDeltaSeconds();
        time += timePassed;
        if (time >= totalTime) {
            time = looping ? time %= totalTime : 0;
            if (randomTime) {
                totalTime = Maths.randomNumberBetween(minTime, maxTime);
            }
            started = false;
            return true;
        }
        return false;
    }

    public Timer randomize() {
        time = Maths.RANDOM.nextFloat() * totalTime;
        return this;
    }

}
