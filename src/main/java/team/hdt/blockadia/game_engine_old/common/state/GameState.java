package team.hdt.blockadia.game_engine_old.common.state;

public interface GameState {
    void setup();

    void drop();

    GameState update();

    void render();
}
