package team.hdt.blockadia.game_engine.common.state;

import team.hdt.blockadia.game_engine.client.rendering.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class IngameState implements GameState {

    @Override
    public void setup() {
        TextureLoader.loadTextures(false);
        TextureLoader.bind(TextureLoader.Textures.SHEET);
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
        glShadeModel(GL_SMOOTH);
        glClearColor(0.53f, 0.8f, 0.98f, 0.0f); // Sky blue
        glClearDepth(1.0);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        // Transparency
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER, 0.0f);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
}
