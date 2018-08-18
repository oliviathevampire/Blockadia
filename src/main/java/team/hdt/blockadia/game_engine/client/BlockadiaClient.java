package team.hdt.blockadia.game_engine.client;

import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.util.GameSide;
import team.hdt.blockadia.game_engine.core.util.GameSideOnly;

@GameSideOnly(GameSide.CLIENT)
public class BlockadiaClient {

    public static final int CLIENT_WIDTH = 856, CLIENT_HEIGHT = 480;

    public static void main(String[] args) {
        Display display = new Display(args[0], CLIENT_WIDTH, CLIENT_HEIGHT);
        display.run();
    }

}