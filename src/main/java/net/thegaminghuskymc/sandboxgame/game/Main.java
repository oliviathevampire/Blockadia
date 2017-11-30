package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Window;

public class Main {

    public static final DummyGame gameLogic = new DummyGame();

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            Window.WindowOptions opts = new Window.WindowOptions();
            opts.cullFace = false;
            opts.compatibleProfile = true;
            opts.antialiasing = true;
            opts.frustumCulling = true;
            GameEngine gameEng = new GameEngine("Husky's Sandbox Game", vSync, opts, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
