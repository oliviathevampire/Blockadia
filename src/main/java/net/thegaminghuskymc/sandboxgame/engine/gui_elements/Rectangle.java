package net.thegaminghuskymc.sandboxgame.engine.gui_elements;

import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;

public class Rectangle {
    private Vector2d pos;
    private Vector2d size;
    public Rectangle(float x, float y, float w, float h){
        pos = new Vector2d(x,y);
        size = new Vector2d(w, h);
    }

    public void show(){
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(pos.x, pos.y);
        GL11.glVertex2d(pos.x, pos.y+size.y);
        GL11.glVertex2d(pos.x+size.x, pos.y+size.y);
        GL11.glVertex2d(pos.x+size.x, pos.y);
        GL11.glEnd();
    }
}
