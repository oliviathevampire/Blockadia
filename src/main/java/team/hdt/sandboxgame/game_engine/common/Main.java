package team.hdt.sandboxgame.game_engine.common;

import org.lwjgl.opengl.GL11;
import team.hdt.sandboxgame.game_engine.common.util.Display;
import team.hdt.sandboxgame.game_engine.common.util.TickRegulator;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockTypes;
import team.hdt.sandboxgame.game_engine.util.FileUtils;
import team.hdt.sandboxgame.game_engine.util.MyFile;

public class Main {

    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 20;

    public static final int FPS_CAP = 100;
    private static final float MAX_DELTA = 0.2f;
    public static final boolean V_SYNC = false;

    private static final float STABLE_DELTA_TIME = 2;

    private static final int TITLE_TEXT_ID = 1;
    private static final MyFile ICON16_FILE = new MyFile(FileUtils.RES_FOLDER, "icon16.png");
    private static final MyFile ICON32_FILE = new MyFile(FileUtils.RES_FOLDER, "icon32.png");
    private static final MyFile ICON128_FILE = new MyFile(FileUtils.RES_FOLDER, "icon128.png");

    public static final int WIDTH = 856, HEIGHT = 480;
    public static Display display;
    private static float aspectRatio;
    private static float delta;
    private static float time = 0;


    public static void main(String[] args) {
        // TODO: Some centralised place for registration of everything
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        aspectRatio = (float) WIDTH / (float) HEIGHT;
        delta = 1f / FPS_CAP;
        BlockTypes.register();

        GameController controller = new GameController();
        controller.setup();

        display = new Display("Husky's Sandbox Game", WIDTH, HEIGHT);

        TickRegulator tickRegulator = new TickRegulator(1000000000L / TICK_RATE);
        TickRegulator frameRegulator = new TickRegulator(1000000000L / FRAME_RATE);

        tickRegulator.start();
        frameRegulator.start();

        while (!display.shouldClose()) {
            // TODO: This setup is not ideal.
            int scheduledFrames = frameRegulator.getScheduledTicks();
            for (int i = 0; i < scheduledFrames; i++) {
                controller.render();
            }
            int scheduledTicks = tickRegulator.getScheduledTicks();
            for (int i = 0; i < scheduledTicks; i++) {
                controller.update();
            }
        }
        display.run();
        controller.drop();
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

}
