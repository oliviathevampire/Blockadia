package net.thegaminghuskymc.sandboxgame.engine.world;

public class WeatherState {
    /** weather states */
    public static final int DAY = (1 << 0);
    public static final int NIGHT = (1 << 1);
    public static final int DAY_ENDING = (1 << 2);
    public static final int NIGHT_ENDING = (1 << 3);
    public static final int RAIN_STARTING = (1 << 4);
    public static final int RAINING = (1 << 5);
    public static final int RAIN_ENDING = (1 << 6);
}
