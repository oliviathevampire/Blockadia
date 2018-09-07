package team.hdt.blockadia.game_engine_old.common.state;

import team.hdt.blockadia.game_engine_old.client.rendering.TextureLoader;

public class MenuState implements GameState {
    @Override
    public void setup() {
    }

    @Override
    public void drop() {
    }

    @Override
    public GameState update() {
        return this;
    }

    @Override
    public void render() {
        TextureLoader.loadTextures(false);
        TextureLoader.bind(TextureLoader.Textures.MAIN_MENU_LOGO);
    }
}