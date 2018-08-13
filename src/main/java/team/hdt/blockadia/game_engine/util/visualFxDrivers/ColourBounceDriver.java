package team.hdt.blockadia.game_engine.util.visualFxDrivers;

import team.hdt.blockadia.game_engine.util.toolbox.Colour;

public class ColourBounceDriver {
	
	private Colour current;
	private Colour startValue;
	private Colour endValue;
	private Colour peakValue;
	private float max = 0;
	private boolean reachedTarget = false;
	
	private float currentTime = 0;
	private float length;

	public ColourBounceDriver(Colour current, Colour peak, float length) {
		this.length = length;
		this.current = current;
		this.startValue = current.duplicate();
		this.endValue = startValue.duplicate();
		this.peakValue = peak.duplicate();
	}
	
	public ColourBounceDriver(Colour current, Colour peak, Colour end, float length) {
		this.length = length;
		this.current = current;
		this.startValue = current.duplicate();
		this.endValue = end.duplicate();
		this.peakValue = peak.duplicate();
	}

	public void update(float delta){
		this.currentTime += delta;
		currentTime %= length;
		float time = currentTime / length;
		calculateValue(time);
	}
	
	protected void calculateValue(float time) {
		if (!reachedTarget && time >= max) {
			max = time;
			if (time < 0.5f) {
				Colour.interpolateColours(startValue, peakValue, time * 2, current);
			} else {
				Colour.interpolateColours(peakValue, endValue, (time - 0.5f) * 2f, current);
			}
		} else {
			reachedTarget = true;
		}
	}

}
