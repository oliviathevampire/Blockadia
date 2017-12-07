package net.thegaminghuskymc.sandboxgame.game.client.renderer.model;

import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLTexture;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.ImageUtils;

public class ModelSkin {

    /** the skin name */
    private String name;

    /** the skin filepath */
    private String filepath;

    /** the gl texture object */
    private GLTexture glTexture;

    public ModelSkin() {
        this(null);
    }

    public ModelSkin(String name) {
        this(name, null);
    }

    public ModelSkin(String name, String filepath) {
        this.name = name;
        this.filepath = filepath;
        this.glTexture = GLH.glhGenTexture();
        this.load();
    }

    public String getName() {
        return (this.name);
    }

    public String getFilepath() {
        return (this.filepath);
    }

    @Override
    public String toString() {
        return (this.getFilepath());
    }

    public void bind(int texture, int target) {
        this.glTexture.bind(texture, target);
    }

    public final GLTexture getGLTexture() {
        return (this.glTexture);
    }

    public final void save() {
        ImageUtils.exportPNGImage(this.getFilepath(), this.getGLTexture().getData());
    }

    public final void load() {
        this.glTexture.setData(ImageUtils.readImage(this.getFilepath()));
    }
}
