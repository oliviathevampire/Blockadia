package team.hdt.blockadia.game_engine.client.rendering.textures;

import team.hdt.blockadia.game_engine.client.glRequestProcessing.GlRequestProcessor;
import team.hdt.blockadia.game_engine.util.MyFile;

public class Texture {

    private boolean loaded = false;
    private int textureID;

    protected Texture() {
    }

    public static TextureBuilder newTexture(MyFile file) {
        return new TextureBuilder(file);
    }

    public static Texture getEmptyTexture() {
        return new Texture();
    }

    public void setTextureID(int id) {
        this.textureID = id;
        loaded = true;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public int getID() {
        return textureID;
    }

    public void delete() {
        loaded = false;
        GlRequestProcessor.sendRequest(new TextureDeleteRequest(textureID));
    }

}
