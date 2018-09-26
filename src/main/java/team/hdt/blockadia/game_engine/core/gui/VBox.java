package team.hdt.blockadia.game_engine.core.gui;

import org.lwjgl.nanovg.NanoVG;

import java.util.ArrayList;

public class VBox extends Parent {

    private ArrayList<Node> children = new ArrayList<Node>();
    private float padding = 1.0f;

    public VBox() {
        super(50, 50, 500, 200);
    }

    public void add(Node node) {
        node.setX(this.getX() + padding);
        node.setY(this.getY());
        children.add(node);
    }

    @Override
    public void update() {

        save();
        beginPath();
        roundedRect(x, y, width, height, 3.0f);
        fillColor(0, 36, 57);
        fill();

        restore();

        save();
        NanoVG.nvgScissor(NanoGui.getVG(), x, y, width, height - 1f);
        for (Node n : children) {
            float height = n.getHeight() + padding;
            translate(0, height);
            n.update();
        }
        NanoVG.nvgResetScissor(NanoGui.getVG());
        restore();

    }

}