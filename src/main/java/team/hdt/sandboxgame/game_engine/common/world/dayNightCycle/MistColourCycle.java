package team.hdt.sandboxgame.game_engine.common.world.dayNightCycle;

import team.hdt.sandboxgame.game_engine.util.toolbox.Colour;

public class MistColourCycle extends ColourCycle{

	private static final Colour NIGHT_COLOUR = new Colour(74, 104, 156, true);
	private static final Colour DAY_COLOUR = new Colour(246, 229, 197, true);
	private static final Colour DAWN_COLOUR = new Colour(255, 174, 217, true);
	private static final Colour DUSK_COLOUR = new Colour(255, 222, 210, true);

	private static final float MIDNIGHT_END = 2.4f;
	private static final float MIDDAY_START = 12f;
	private static final float MIDDAY_END = 14.4f;

	public MistColourCycle() {
		super(NIGHT_COLOUR, DAY_COLOUR, DAWN_COLOUR, DUSK_COLOUR, MIDNIGHT_END, MIDDAY_START, MIDDAY_END);
	}
	
}
