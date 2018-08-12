package team.hdt.sandboxgame.game_engine.common;

import team.hdt.sandboxgame.game_engine.common.util.Display;

public class Main {

    public static int WINDOW_WIDTH = 300, WINDOW_HEIGHT = 300;
    public static Display display;

    public static void main(String[] args) {
        display = new Display("Husky's Sandbox Game", 300, 300);
        display.run();
    }

}