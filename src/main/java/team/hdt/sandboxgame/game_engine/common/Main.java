package team.hdt.sandboxgame.game_engine.common;

import team.hdt.sandboxgame.game_engine.common.util.Display;

public class Main {

    public static void main(String[] args) {
        Display display = new Display("Husky's Sandbox Game", 300, 300);
        display.run();
    }

}