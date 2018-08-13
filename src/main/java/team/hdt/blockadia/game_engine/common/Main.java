package team.hdt.blockadia.game_engine.common;

import team.hdt.blockadia.game_engine.client.glRequestProcessing.GlRequestProcessor;
import team.hdt.blockadia.game_engine.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine.client.rendering.EngineMaster;
import team.hdt.blockadia.game_engine.client.rendering.MasterRenderer;
import team.hdt.blockadia.game_engine.common.gameManaging.GameManager;
import team.hdt.blockadia.game_engine.common.util.Display;
import team.hdt.blockadia.game_engine.util.FileUtils;
import team.hdt.blockadia.game_engine.util.MyFile;

import java.util.Calendar;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 20;

    public static final int FPS_CAP = 100;
    private static final float MAX_DELTA = 0.2f;
    public static final boolean V_SYNC = false;

    private static final float STABLE_DELTA_TIME = 2;

    public static String version = "0.0.1";

    private static final int TITLE_TEXT_ID = 1;
    private static final MyFile ICON16_FILE = new MyFile(FileUtils.RES_FOLDER, "icon16.png");
    private static final MyFile ICON32_FILE = new MyFile(FileUtils.RES_FOLDER, "icon32.png");
    private static final MyFile ICON128_FILE = new MyFile(FileUtils.RES_FOLDER, "icon128.png");

    public static final int WIDTH = 856, HEIGHT = 480;
    public static Display display;
    private static float aspectRatio;
    private static float delta;
    private static float time = 0;
    private static int ticker = 0;
    private static boolean error = false;

    public static float TIME_SPEED = 1;

    public static void main(String[] args) {
        EngineMaster.init(Camera.getCamera());
        FirstScreenUi screen = new FirstScreenUi();
        GuiMaster.addComponent(screen, 0, 0, 1, 1);
        while (!screen.isReady()) {
            GuiMaster.updateGuis();
            MasterRenderer.renderGuis();
            EngineMaster.update();
        }
        GameManager.init();
        GlRequestProcessor.completeAllRequests();
        while (!glfwWindowShouldClose(display.window)) {
            GameManager.update();
            GameManager.render();
        }
        GameManager.cleanUp();
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

    public static float getGameTime(){
        return time;
    }

}
