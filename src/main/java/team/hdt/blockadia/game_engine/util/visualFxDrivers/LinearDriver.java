package team.hdt.blockadia.game_engine.util.visualFxDrivers;

public class LinearDriver extends ValueDriver{
	
	private float startValue;
	private float difference;

	public LinearDriver(float startValue, float endValue, float length) {
		super(length);
		this.startValue = startValue;
		this.difference = endValue - startValue;
	}

	@Override
	protected float calculateValue(float time) {
		return startValue + (time*difference);
	}
	
	

}
