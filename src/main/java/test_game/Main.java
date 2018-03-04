package test_game;

import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;

public class Main {

    public static void main(String[] args) {
        GameEngineClient engine = new GameEngineClient();
        engine.initialize();

        engine.load();

        engine.loop();
        engine.deinitialize();
    }

}
