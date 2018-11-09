package team.hdt.blockadia.engine.core.gui;

import team.hdt.blockadia.engine.core.util.math.vectors.Vectors2f;

public class UIScene {

    private Vectors2f location = new Vectors2f();
    private Bounds clipBounds;

    private Parent parent = null;

    public UIScene(Parent parent) {
        this(parent, new Bounds(new Vectors2f(), new Vectors2f(200, 200)));
    }

    public UIScene(Parent parent, Bounds bounds) {
        clipBounds = bounds;
//		NanoGui.init();
        this.parent = parent;
    }

    public void update() {
        if (parent != null) {
            parent.update();
        }
    }
}