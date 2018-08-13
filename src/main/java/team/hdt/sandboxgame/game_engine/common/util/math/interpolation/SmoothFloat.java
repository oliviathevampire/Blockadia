package team.hdt.sandboxgame.game_engine.common.util.math.interpolation;

import team.hdt.sandboxgame.game_engine.util.toolbox.Maths;

public class SmoothFloat {

	private final float agility;

	private float target;
	private float actual;

	public SmoothFloat(float initialValue, float agility) {
		this.target = initialValue;
		this.actual = initialValue;
		this.agility = agility;
	}

	public void update(float delta) {
		float offset = target - actual;
		float factor = delta * agility;
		if (factor > 1) {
			actual = target;
		} else {
			actual += offset * factor;
		}
	}
	
	public void clampTarget(float min, float max){
		this.target = Maths.clamp(target, min, max);
	}
	
	public void cancelTarget(){
		this.target = actual;
	}
	
	public void invertActual(){
		this.actual = -actual;
	}

	public void setTarget(float target) {
		this.target = target;
	}

	public void increaseTarget(float increase) {
		this.target += increase;
	}

	public void force(float newValue) {
		this.actual = newValue;
		this.target = newValue;
	}
	
	public void forceOnlyActualValue(float newValue){
		float difference = target - actual;
		this.actual = newValue;
		this.target = actual + difference;
	}

	public void instantIncrease(float increase) {
		this.actual += increase;
	}
	
	public boolean reached(){
		return Math.abs(actual-target) < 0.001f;
	}

	public void increaseAll(float increase) {
		this.actual += increase;
		this.target += increase;
	}

	public float get() {
		return actual;
	}

}
