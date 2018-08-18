package team.hdt.blockadia.game_engine_old.client;

import team.hdt.blockadia.game_engine.core.Display;

import java.util.Calendar;

public class ClientMain {

    public static final int FPS_CAP = 100;
    public static final boolean V_SYNC = false;
    public static final int WIDTH = 856, HEIGHT = 480;
    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 20;
    private static final float MAX_DELTA = 0.2f;
    private static final float STABLE_DELTA_TIME = 2;
    private static final int TITLE_TEXT_ID = 1;
//    private static final MyFile ICON16_FILE = new MyFile(FileUtils.RES_FOLDER, "icon16.png");
//    private static final MyFile ICON32_FILE = new MyFile(FileUtils.RES_FOLDER, "icon32.png");
//    private static final MyFile ICON128_FILE = new MyFile(FileUtils.RES_FOLDER, "icon128.png");
    public static String version = "0.0.1";
    public static Display display;
    public static float TIME_SPEED = 1;
    private static float aspectRatio;
    private static float delta;
    private static float time = 0;
    private static int ticker = 0;
    private static boolean error = false;

    public static void main(String[] args) {
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * @return The time since the last frame in seconds.
     */
    public static float getDeltaSeconds() {
        return delta;
    }

    /**
     * @return The aspect ratio of the display (width/height).
     */
    public static float getAspectRatio() {
        return aspectRatio;
    }

    /**
     * @return The game time.
     */
    public static float getTime() {
        return time;
    }

    public static float getGameSeconds() {
        return getDeltaSeconds() * TIME_SPEED;
    }

    public static float getDeltaHours() {
        return (getDeltaSeconds() * TIME_SPEED) / Calendar.HOUR;
    }

    public static float getGameTime() {
        return time;
    }

}
