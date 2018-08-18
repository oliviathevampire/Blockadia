package team.hdt.blockadia.game_engine.core.world.dayNightCycle;

import team.hdt.blockadia.game_engine_old.util.toolbox.Colour;

public class HorizonColourCycle extends ColourCycle {

    private static final Colour NIGHT_COLOUR = new Colour(51, 51, 108, true);
    private static final Colour DAY_COLOUR = new Colour(0.6f, 0.9f, 1f);
    private static final Colour DAWN_COLOUR = new Colour(238, 124, 145, true);
    private static final Colour DUSK_COLOUR = new Colour(255, 160, 119, true);

    private static final float MIDNIGHT_END = 2.4f;
    private static final float MIDDAY_START = 12f;
    private static final float MIDDAY_END = 14.4f;

    public HorizonColourCycle() {
        super(NIGHT_COLOUR, DAY_COLOUR, DAWN_COLOUR, DUSK_COLOUR, MIDNIGHT_END, MIDDAY_START, MIDDAY_END);
    }

}
