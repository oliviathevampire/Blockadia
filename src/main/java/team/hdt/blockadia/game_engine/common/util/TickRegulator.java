package team.hdt.blockadia.game_engine.common.util;

public class TickRegulator {
    private final long interval;

    private long lastTime;

    public TickRegulator(long interval) {
        this.interval = interval;
    }

    public void start() {
        this.lastTime = System.nanoTime();
    }

    public int getScheduledTicks() {
        long time = System.nanoTime();
        long deltaTime = time - this.lastTime;
        return (int) (deltaTime / this.interval);
    }

    public long getInterval() {
        return this.interval;
    }
}
