package team.hdt.blockadia.engine.core_rewrite.util;

public class Timer {

	/** How many full ticks have turned over since the last call to updateTimer(), capped at 10. */
	public int elapsedTicks;
	/** How much time has elapsed since the last tick, in ticks, for use by display rendering routines (range: 0.0 - 1.0). */
	public float renderPartialTicks;
	/** How much time has elapsed since the last tick, in ticks (range: 0.0 - 1.0). */
	public float elapsedPartialTicks;

	private long lastSyncSysClock;
	private float tickLength;

	/**
	 * @param tps
	 *            The amount of times the timer will update per update
	 */
	public Timer(float tps) {
		this.tickLength = 1000.0f / tps;
		this.lastSyncSysClock = System.currentTimeMillis();
	}

	public void updateTimer() {
		long i = System.currentTimeMillis();
		this.elapsedPartialTicks = (i - this.lastSyncSysClock) / this.tickLength;
		this.lastSyncSysClock = i;
		this.renderPartialTicks += this.elapsedPartialTicks;
		this.elapsedTicks = (int) this.renderPartialTicks;
		this.renderPartialTicks -= this.elapsedTicks;
	}
	
	public float getTickLength() {
		return tickLength;
	}
}