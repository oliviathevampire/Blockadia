package team.hdt.blockadia.game_engine.client;

import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.util.GameSide;
import team.hdt.blockadia.game_engine.core.util.GameSideOnly;

@GameSideOnly(GameSide.CLIENT)
public class BlockadiaClient {

    private static final int CLIENT_WIDTH = 856, CLIENT_HEIGHT = 480;

    public static void main(String[] args) {
        String title;
        if(args[0] != null) {
            title = args[0];
        } else {
            title = "Blockadia";
        }
        Display display = new Display(title, CLIENT_WIDTH, CLIENT_HEIGHT);
        display.run();
    }

}