package team.hdt.blockadia.game_engine_old.common.gameManaging;

import team.hdt.blockadia.game_engine_old.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine_old.common.Camera;

public class GameStateManager {

    private GameState currentGameState = null;
    private GameState nextGameState = GameState.CAMERA;
    private GameState suggestedGameState;

    public void endState(GameState state) {
        if (state == nextGameState) {
            this.nextGameState = null;
        }
    }

    public GameState getState() {
        return currentGameState;
    }

    public void setState(GameState state) {
        this.nextGameState = state;
    }

    public void suggestState(GameState state) {
        this.suggestedGameState = state;
    }

    public void update() {
        if (nextGameState == null) {
            nextGameState = suggestedGameState;
        }
        suggestedGameState = null;
        if (nextGameState != currentGameState) {
            changeState(nextGameState);
        }
    }

    private void changeState(GameState state) {
        revertSettings();
        this.currentGameState = state;
        this.nextGameState = state;
        if (currentGameState != null) {
            currentGameState.init();
        } else {
            System.out.println("Normal mode");
        }
    }

    private void revertSettings() {
        Camera.getCamera().enable(true);
        GuiMaster.enableMouseInteraction(true);
    }

}
