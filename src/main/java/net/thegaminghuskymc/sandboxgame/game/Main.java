package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Window;

public class Main {

    public static final DummyGame gameLogic = new DummyGame();
    public static String MODID = "sandboxgame";

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            Window.WindowOptions opts = new Window.WindowOptions();
            opts.cullFace = false;
            opts.compatibleProfile = true;
            opts.antialiasing = false;
            opts.frustumCulling = true;
            opts.showFps = true;
            GameEngine gameEng = new GameEngine("Husky's Sandbox Game", vSync, opts, gameLogic);
            gameEng.initialize();
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            GameEngine engine = GameEngine.instance();
            engine.deinitialize();
            System.exit(-1);
        }
    }
}
