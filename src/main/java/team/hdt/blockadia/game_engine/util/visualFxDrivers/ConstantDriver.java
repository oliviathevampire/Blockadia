package team.hdt.blockadia.game_engine.util.visualFxDrivers;


public class ConstantDriver extends ValueDriver{
	
	private float value;
	
	public ConstantDriver(float constant){
		super(1);
		this.value = constant;
	}

	@Override
	protected float calculateValue(float time) {
		return value;
	}

	public void setValue(float value){
		this.value = value;
	}
	
	
}
