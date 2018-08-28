package team.hdt.blockadia.game_engine.client;

import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.util.GameSide;
import team.hdt.blockadia.game_engine.core.util.GameSideOnly;

@GameSideOnly(GameSide.CLIENT)
public class BlockadiaClient extends MainExtras {

    public static void main(String[] args) {
        System.out.println(getHeight());
        Display display = new Display("Blockadia", getWidth(), getHeight());
        display.run();
    }
    /**
     * |=========================================|
     * |please read TODO list before start coding|
     * |=========================================|
     */

}