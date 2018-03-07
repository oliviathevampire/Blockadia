package net.thegaminghuskymc.sandboxgame.game.client.tests;

import net.thegaminghuskymc.sandboxgame.game.client.BlockitectEngineClient;

public class LoadingUnloadingTest {

    public static void main(String[] args) {

        /* 1 */
        // initialize engine
        BlockitectEngineClient engine = new BlockitectEngineClient();
        engine.initialize();

        // load resources (mods)
        engine.load();

        engine.loop();
        engine.uninitialized();
    }
}