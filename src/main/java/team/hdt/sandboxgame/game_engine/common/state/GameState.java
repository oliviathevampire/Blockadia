package team.hdt.sandboxgame.game_engine.common.state;

public interface GameState {
    void setup();

    void drop();

    GameState update();

    void render();
}
