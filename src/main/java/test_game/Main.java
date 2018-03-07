package test_game;

import net.thegaminghuskymc.sandboxgame.game.client.BlockitectEngineClient;

public class Main {

    public static void main(String[] args) {
        BlockitectEngineClient engine = new BlockitectEngineClient();
        engine.initialize();

        engine.load();

        engine.loop();
        engine.uninitialized();
    }

}
