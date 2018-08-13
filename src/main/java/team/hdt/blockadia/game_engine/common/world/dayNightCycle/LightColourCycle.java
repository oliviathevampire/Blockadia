package team.hdt.blockadia.game_engine.common.world.dayNightCycle;

import team.hdt.blockadia.game_engine.util.toolbox.Colour;

public class LightColourCycle extends ColourCycle {

	private static final Colour NIGHT_COLOUR = new Colour(0.35f, 0.38f, 0.8f);
	private static final Colour DAY_COLOUR = new Colour(1f, 0.95f, 0.95f);
	private static final Colour DAWN_COLOUR = new Colour(1f, 0.45f, 0.56f);
	private static final Colour DUSK_COLOUR = new Colour(1f, 0.87f, 0.6f);

	private static final float MIDNIGHT_END = 2.4f;
	private static final float MIDDAY_START = 12f;
	private static final float MIDDAY_END = 14.4f;

	public LightColourCycle() {
		super(NIGHT_COLOUR, DAY_COLOUR, DAWN_COLOUR, DUSK_COLOUR, MIDNIGHT_END, MIDDAY_START, MIDDAY_END);
	}

}
