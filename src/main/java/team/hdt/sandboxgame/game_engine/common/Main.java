package team.hdt.sandboxgame.game_engine.common;

import team.hdt.sandboxgame.game_engine.common.util.Display;
import team.hdt.sandboxgame.game_engine.common.util.TickRegulator;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockTypes;

public class Main implements Runnable {

    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 20;

    public static int WINDOW_WIDTH = 856, WINDOW_HEIGHT = 480;
    public static Display display;
    private final Thread gameLoopThread;

    private Main() {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
    }

    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void run() {
        // TODO: Some centralised place for registration of everything
        BlockTypes.register();

        GameController controller = new GameController();
        controller.setup();

        display = new Display("Husky's Sandbox Game", WINDOW_WIDTH, WINDOW_HEIGHT);

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

}
