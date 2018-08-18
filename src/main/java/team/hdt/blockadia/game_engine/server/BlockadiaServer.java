package team.hdt.blockadia.game_engine.server;

import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.util.GameSide;
import team.hdt.blockadia.game_engine.core.util.GameSideOnly;

@GameSideOnly(GameSide.SERVER)
public class BlockadiaServer {

    public static final int SERVER_WIDTH = 856, SERVER_HEIGHT = 480;

    public static void main(String[] args) {
        Display display = new Display("Blockadia Server", SERVER_WIDTH, SERVER_HEIGHT);
        display.run();
    }

}
