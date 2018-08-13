package team.hdt.blockadia.game_engine.util.visualFxDrivers;

import team.hdt.blockadia.game_engine.util.toolbox.Maths;

public class SlideDriver extends ValueDriver {

	private float startValue;
	private float endValue;
	private float max = 0;
	private boolean reachedTarget = false;

	public SlideDriver(float start, float end, float length) {
		super(length);
		this.startValue = start;
		this.endValue = end;
	}

	@Override
	protected float calculateValue(float time) {
		if(!reachedTarget && time >= max){
			max = time;
			return Maths.cosInterpolate(startValue, endValue, time);
		}else{
			reachedTarget = true;
			return endValue;
		}
	}

}
