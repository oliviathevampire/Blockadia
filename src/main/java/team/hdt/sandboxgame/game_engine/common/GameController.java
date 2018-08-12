package team.hdt.sandboxgame.game_engine.common;

import team.hdt.sandboxgame.game_engine.common.state.GameState;
import team.hdt.sandboxgame.game_engine.common.state.IngameState;

/**
 * Controls the state of the game
 */
public class GameController {

    private GameState state = new IngameState();

    public void setup() {
        this.state.setup();
    }

    public void update() {
        GameState newState = this.state.update();
        if (this.state != newState) {
            newState.setup();
            this.state.drop();
        }
        this.state = newState;
    }

    public void render() {
        this.state.render();
    }

    public void drop() {
        this.state.drop();
    }

}
