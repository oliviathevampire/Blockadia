package net.thegaminghuskymc.sandboxgame.engine.gui_elements;

import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;

public class Character {
    Vector2d pos;
    Vector2d size;
    private int charid;
    private int bitmapID;

    Character(int id_, int x, int y, int w, int h){
        charid = id_;
        pos = new Vector2d(x, y);
        size = new Vector2d(w, h);
    }

    void display(){
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, bitmapID);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2d(0, size.y);

        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2d(size.x, size.y);

        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2d(size.x, 0);

        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2d(0, 0);

        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    void setBitmap(int id){
        bitmapID = id;
    }

    int getID(){
        return charid;
    }
}
