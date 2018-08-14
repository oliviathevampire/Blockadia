package team.hdt.blockadia.test.engine;

import team.hdt.blockadia.test.engine.graph.item.GameItem;

public interface IHud {

    GameItem[] getGameItems();

    default void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}