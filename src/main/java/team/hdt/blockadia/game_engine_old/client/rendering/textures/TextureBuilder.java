package team.hdt.blockadia.game_engine_old.client.rendering.textures;

import team.hdt.blockadia.game_engine_old.client.glRequestProcessing.GlRequestProcessor;
import team.hdt.blockadia.game_engine_old.client.resourceProcessing.RequestProcessor;
import team.hdt.blockadia.game_engine_old.util.MyFile;
import team.hdt.blockadia.game_engine_old.util.toolbox.Colour;

public class TextureBuilder {

    private boolean clampEdges = false;
    private boolean mipmap = true;
    private boolean anisotropic = true;
    private boolean nearest = false;
    private boolean clampToBorder = false;
    private Colour borderColour = new Colour(0, 0, 0, 0);

    private MyFile file;

    protected TextureBuilder(MyFile textureFile) {
        this.file = textureFile;
    }

    public TextureBuilder clampEdges() {
        this.clampEdges = true;
        clampToBorder = false;
        return this;
    }

    public TextureBuilder clampToBorder(float r, float g, float b, float a) {
        borderColour.setColour(r, g, b, a);
        clampToBorder = true;
        clampEdges = false;
        return this;
    }

    public TextureBuilder noMipMap() {
        this.mipmap = false;
        this.anisotropic = false;
        return this;
    }

    public TextureBuilder nearestFiltering() {
        this.mipmap = false;
        this.nearest = true;
        return this;
    }

    public TextureBuilder noFiltering() {
        this.anisotropic = false;
        return this;
    }

    protected boolean isClampEdges() {
        return clampEdges;
    }

    protected Colour getBorderColour() {
        return borderColour;
    }

    protected boolean isClampToBorder() {
        return clampToBorder;
    }

    protected boolean isMipmap() {
        return mipmap;
    }

    protected boolean isAnisotropic() {
        return anisotropic;
    }

    protected boolean isNearest() {
        return nearest;
    }

    protected MyFile getFile() {
        return file;
    }

    /**
     * Creates a new texture and sends it to be loaded by the loader thread.
     *
     * @return The texture.
     */
    public Texture createInBackground() {
        Texture texture = new Texture();
        RequestProcessor.sendRequest(new TextureLoadRequest(texture, this));
        return texture;
    }

    /**
     * Creates a new texture, carries out the CPU loading, and sends to the main thread for GL loading.
     *
     * @return The texture.
     */
    public Texture createInSecondThread() {
        Texture texture = new Texture();
        TextureLoadRequest request = new TextureLoadRequest(texture, this);
        request.doResourceRequest();
        GlRequestProcessor.sendRequest(request);
        return texture;
    }

    /**
     * Creates a new texture, carries out the CPU loading, and loads to OpenGL.
     *
     * @return The texture.
     */
    public Texture create() {
        Texture texture = new Texture();
        TextureLoadRequest request = new TextureLoadRequest(texture, this);
        request.doResourceRequest();
        request.executeGlRequest();
        return texture;
    }

}
