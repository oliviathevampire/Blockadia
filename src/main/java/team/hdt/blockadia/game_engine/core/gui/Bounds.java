package team.hdt.blockadia.game_engine.core.gui;

import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors2f;

public class Bounds {

    private Vectors2f startBounds;
    private Vectors2f endBounds;

    public Bounds(Vectors2f startBounds, Vectors2f endBounds) {
        super();
        this.startBounds = startBounds;
        this.endBounds = endBounds;
    }

    public Vectors2f getStartBounds() {
        return startBounds;
    }

    public void setStartBounds(Vectors2f startBounds) {
        this.startBounds = startBounds;
    }

    public Vectors2f getEndBounds() {
        return endBounds;
    }

    public void setEndBounds(Vectors2f endBounds) {
        this.endBounds = endBounds;
    }
}