package team.hdt.sandboxgame.game_engine.client.gui;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors4f;

public class GuiTexture {

    private int texture;
    private Vectors2f position;
    private Vectors2f scale;


    public GuiTexture(int texture, Vectors2f position, Vectors2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public Vectors2f getPosition() {
        return position;
    }

    public void setPosition(Vectors2f position) {
        this.position = position;
    }

    public Vectors2f getScale() {
        return scale;
    }

    public void setScale(Vectors2f scale) {
        this.scale = scale;
    }

    public void setColor(Vectors4f color) {
    }
}
