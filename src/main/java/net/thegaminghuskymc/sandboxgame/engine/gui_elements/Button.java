package net.thegaminghuskymc.sandboxgame.engine.gui_elements;

import net.thegaminghuskymc.sandboxgame.engine.MouseInput;
import net.thegaminghuskymc.sandboxgame.engine.Window;
import org.joml.Vector2d;

import static org.lwjgl.glfw.GLFW.*;

public class Button extends MouseInput {
    private Vector2d pos;
    private Vector2d size;
    private String text;
    private Vector2d currentPos;

    private Rectangle rect;
    private MouseInput mi;
    private double timeout = 0.10;
    private double curTime;
    private int action = -1;
    private Window window;

    private Font font;

    public Button(Window window, float x, float y, float w, float h, String text, int action) {
        mi = new MouseInput();
        currentPos = new Vector2d(0, 0);
        rect = new Rectangle(x, y, w, h);
        pos = new Vector2d(x, y);
        size = new Vector2d(w, h);
        this.text = text;
        this.action = action;
        this.window = window;
        font = new Font(window);
        font.loadFont("roboto");
        font.generateText(text);
    }

    public void show() {
        //rect.show();
        font.showText(pos);
        checkPressed();
    }

    private void checkPressed(){
        currentPos = mi.getCurrentPos();
        if (mi.isLeftButtonPressed()) {
            if (glfwGetTime() >= curTime) {
                curTime = glfwGetTime() + timeout;
                if (currentPos.x > pos.x && currentPos.x < pos.x + size.x) {
                    if (window.getHeight()-currentPos.y > pos.y && window.getHeight()-currentPos.y < pos.y+size.y) {
                        System.out.println("pressed!");
                    }
                }
            }
        }
    }
}
