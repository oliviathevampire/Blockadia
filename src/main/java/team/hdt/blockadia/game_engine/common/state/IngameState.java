package team.hdt.blockadia.game_engine.common.state;

import team.hdt.blockadia.game_engine.client.rendering.TextureLoader;
import team.hdt.blockadia.game_engine.common.world.Arena;
import team.hdt.blockadia.game_engine.common.world.player.BendingStyle;
import team.hdt.blockadia.game_engine.common.world.player.Player;

import static org.lwjgl.opengl.GL11.*;

public class IngameState implements GameState {
    private Arena arena;
    private Player player;

    @Override
    public void setup() {
        this.arena = new Arena();
        this.arena.genDemoBlocks();
        this.player = new Player(BendingStyle.Element.FIRE, this.arena, 10, 17, 10);
        this.player.processMouse();
        TextureLoader.loadTextures(false);
        TextureLoader.bind(TextureLoader.Textures.SHEET);
    }

    @Override
    public void drop() {
    }

    @Override
    public GameState update() {
        this.player.update();
        this.arena.update();

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

        this.arena.render();
        this.player.render();
    }
}
