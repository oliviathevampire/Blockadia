package team.hdt.blockadia.test.game;

import team.hdt.blockadia.test.engine.GameEngine;
import team.hdt.blockadia.test.engine.Window;

public class Main {

    public static final int WIDTH = 856, HEIGHT = 480;

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            Window.WindowOptions opts = new Window.WindowOptions();
            opts.cullFace = true;
            opts.showFps = true;
            opts.compatibleProfile = true;
            GameEngine gameEng = new GameEngine("Blockadia", WIDTH, HEIGHT, vSync, opts, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }

}