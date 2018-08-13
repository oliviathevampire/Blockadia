package team.hdt.sandboxgame.game_engine.common.world.dayNightCycle;

import team.hdt.sandboxgame.game_engine.common.world.misc.EnvironmentVariables;
import team.hdt.sandboxgame.game_engine.util.toolbox.Colour;
import team.hdt.sandboxgame.game_engine.util.toolbox.Maths;

public class DayNightCycle {

	private LightColourCycle lightCycle = new LightColourCycle();
	private SkyColourCycle skyColCycle = new SkyColourCycle();
	private HorizonColourCycle horizonColCycle = new HorizonColourCycle();
	private MistColourCycle mistCycle = new MistColourCycle();

	public void update(){
		float time = getTime();
		Colour colour = lightCycle.getColour(time);
		EnvironmentVariables.skyColour = horizonColCycle.getColour(time);
		EnvironmentVariables.MIST_COL = mistCycle.getColour(time);
		EnvironmentVariables.horizonColour = skyColCycle.getColour(time);
		EnvironmentVariables.getVariables().setLightColour(colour);
		updateMistAmount(time);
		calculateStarBrightness(time);
	}
	
	private float getTime(){
		float time = 0;
		return time;
	}
	
	private void updateMistAmount(float time){
		float mistyness;
		if(time > 0.5f){
			mistyness = Maths.smoothStep(15f/24f, 18/24f, time);
		}else{
			mistyness = 1f - Maths.smoothStep(7f/24, 10f/24, time);
		}
		mistyness = 1 - mistyness;
		EnvironmentVariables.MIST_VALS.x = 15 + mistyness * 20;
		EnvironmentVariables.MIST_VALS.y = 75 + mistyness * 50;
	}
	
	private void calculateStarBrightness(float time){
		if(time > 0.5f){
			EnvironmentVariables.starBrightness = Maths.smoothStep(22/24f, 24/24f, time);
		}else{
			EnvironmentVariables.starBrightness = 1f - Maths.smoothStep(3f/24, 5f/24, time);
		}
	}
	
}
