package team.hdt.sandboxgame.game_engine.common;

import team.hdt.sandboxgame.game_engine.common.util.Display;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockTypes;

public class Main {

    public static int WINDOW_WIDTH = 856, WINDOW_HEIGHT = 480;
    public static Display display;

    public static void main(String[] args) {
        // TODO: Some centralised place for registration of everything
        BlockTypes.register();

        display = new Display("Husky's Sandbox Game", WINDOW_WIDTH, WINDOW_HEIGHT);
        display.run();
    }
}
