package team.hdt.blockadia.test;

import team.hdt.blockadia.test.idk.DummyGame;

public class Main {

    public static final int WIDTH = 856, HEIGHT = 480;

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEng = new GameEngine("Blockadia", WIDTH, HEIGHT, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }

}