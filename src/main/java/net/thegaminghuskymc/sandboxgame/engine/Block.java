package net.thegaminghuskymc.sandboxgame.engine;

import net.thegaminghuskymc.sandboxgame.engine.graph.Mesh;
import net.thegaminghuskymc.sandboxgame.engine.items.GameItem;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Block extends GameItem {

    private boolean selected;

    private Mesh[] meshes;

    private final Vector3f position;

    private float scale;

    private final Quaternionf rotation;

    private int textPos;

    private boolean disableFrustumCulling;

    private boolean insideFrustum;

    public Block() {
        selected = false;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Quaternionf();
        textPos = 0;
        insideFrustum = true;
        disableFrustumCulling = false;
    }

    public Block(Mesh mesh) {
        this();
        this.meshes = new Mesh[]{mesh};
    }

    public Block(Mesh[] meshes) {
        this();
        this.meshes = meshes;
    }

    public void Render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        GL11.glTranslatef(0f,0.0f,-7f);
        GL11.glRotatef(45f,0.0f,1.0f,0.0f);
        GL11.glColor3f(0.5f,0.5f,1.0f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(1.0f,1.0f,0.0f);
        GL11.glVertex3f( 1.0f, 1.0f,-1.0f);
        GL11.glVertex3f(-1.0f, 1.0f,-1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
        GL11.glColor3f(1.0f,0.5f,0.0f);
        GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
        GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
        GL11.glColor3f(1.0f,0.0f,0.0f);
        GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
        GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
        GL11.glColor3f(1.0f,1.0f,0.0f);
        GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glVertex3f(-1.0f, 1.0f,-1.0f);
        GL11.glVertex3f( 1.0f, 1.0f,-1.0f);
        GL11.glColor3f(0.0f,0.0f,1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f,-1.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
        GL11.glColor3f(1.0f,0.0f,1.0f);
        GL11.glVertex3f( 1.0f, 1.0f,-1.0f);
        GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
        GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
        GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
        GL11.glEnd();

    }

    public void renderCubes() {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glColor3f(1.0f, 0.5f, 0.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glColor3f(1.0f, 1.0f, 0.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glColor3f(1.0f, 0.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glEnd();
    }


    public void render() {

        glBegin(GL_QUADS);
        // top
        glColor3f(1.0f, 0.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertex3f(-0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, 0.5f, -0.5f);
        glVertex3f(-0.5f, 0.5f, -0.5f);

        glEnd();

        glBegin(GL_QUADS);
        // front
        glColor3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 0.0f, 1.0f);
        glVertex3f(0.5f, -0.5f, 0.5f);
        glVertex3f(0.5f, 0.5f, 0.5f);
        glVertex3f(-0.5f, 0.5f, 0.5f);
        glVertex3f(-0.5f, -0.5f, 0.5f);

        glEnd();

        glBegin(GL_QUADS);
        // right
        glColor3f(0.0f, 0.0f, 1.0f);
        glNormal3f(1.0f, 0.0f, 0.0f);
        glVertex3f(0.5f, 0.5f, -0.5f);
        glVertex3f(0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, -0.5f, 0.5f);
        glVertex3f(0.5f, -0.5f, -0.5f);

        glEnd();

        glBegin(GL_QUADS);
        // left
        glColor3f(0.0f, 0.0f, 0.5f);
        glNormal3f(-1.0f, 0.0f, 0.0f);
        glVertex3f(-0.5f, -0.5f, 0.5f);
        glVertex3f(-0.5f, 0.5f, 0.5f);
        glVertex3f(-0.5f, 0.5f, -0.5f);
        glVertex3f(-0.5f, -0.5f, -0.5f);

        glEnd();

        glBegin(GL_QUADS);
        // bottom
        glColor3f(0.5f, 0.0f, 0.0f);
        glNormal3f(0.0f, -1.0f, 0.0f);
        glVertex3f(0.5f, -0.5f, 0.5f);
        glVertex3f(-0.5f, -0.5f, 0.5f);
        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f(0.5f, -0.5f, -0.5f);

        glEnd();

        glBegin(GL_QUADS);
        // back
        glColor3f(0.0f, 0.5f, 0.0f);
        glNormal3f(0.0f, 0.0f, -1.0f);
        glVertex3f(0.5f, 0.5f, -0.5f);
        glVertex3f(0.5f, -0.5f, -0.5f);
        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f(-0.5f, 0.5f, -0.5f);

        glEnd();
    }

}
