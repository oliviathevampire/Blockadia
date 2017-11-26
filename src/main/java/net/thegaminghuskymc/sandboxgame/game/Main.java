package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.IGameLogic;
import net.thegaminghuskymc.sandboxgame.engine.Window;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            Window.WindowOptions opts = new Window.WindowOptions();
            opts.cullFace = false;
            opts.compatibleProfile = true;
            opts.antialiasing = false;
            opts.frustumCulling = true;
            GameEngine gameEng = new GameEngine("Husky's Sandbox Game", vSync, opts, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
