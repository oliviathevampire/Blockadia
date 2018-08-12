package team.hdt.sandboxgame.game_engine.common.state;

import team.hdt.sandboxgame.game_engine.client.rendering.TextureLoader;

public class MenuState implements GameState {
    @Override
    public void setup() {
    }

    @Override
    public void render() {
        TextureLoader.loadTextures(false);
        TextureLoader.bind(TextureLoader.Textures.MAIN_MENU_LOGO);
    }
}
