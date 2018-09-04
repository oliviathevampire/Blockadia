package team.hdt.blockadia.game_engine.server;

import team.hdt.blockadia.game_engine.client.MainExtras;
import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.util.GameSide;
import team.hdt.blockadia.game_engine.core.util.GameSideOnly;

@GameSideOnly(GameSide.SERVER)
public class BlockadiaServer extends MainExtras {

    public static void main(String[] args) {
        Display display = new Display("Blockadia Server", getWidth(), getHeight());
        display.run();
    }

}
