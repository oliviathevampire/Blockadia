package team.hdt.blockadia.game_engine.client;

import java.util.ArrayList;
import java.util.List;

public class MainExtras {

    //need to add mouse IO into these variables
    public static int MouseX = 0;
    public static int MouseY = 0;
    public static final int FPS_CAP = 100;
    public static final boolean V_SYNC = false;
    public static final int WIDTH = 720, HEIGHT = WIDTH * 9 / 16;
    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 20;
    private static final float MAX_DELTA = 0.2f;
    private static final float STABLE_DELTA_TIME = 2;
    private static final int TITLE_TEXT_ID = 1;
    public static String version = "0.0.1";
    private static float delta;
    private static float time = 0;
    private static boolean error = false;
    public static boolean isConnetedServer = false;
    // if need can be removed.
    public List Blocks = new ArrayList();
    public List Items = new ArrayList();
    public List Entity = new ArrayList();

    public static int getMouseX(){
        return MouseX;
    }

    public static int getMouseY(){
        return MouseY;
    }
    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static float getDeltaSeconds() {
        return delta;
    }

    public static float getTime() {
        return time;
    }

    public static float getGameTime() {
        return time;
    }

    public static boolean isIsConnetedServer() {
        return isConnetedServer;
    }
}
