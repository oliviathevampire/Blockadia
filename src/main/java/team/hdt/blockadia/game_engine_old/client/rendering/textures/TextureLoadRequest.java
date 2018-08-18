package team.hdt.blockadia.game_engine_old.client.rendering.textures;

import team.hdt.blockadia.game_engine_old.client.glRequestProcessing.GlRequest;
import team.hdt.blockadia.game_engine_old.client.glRequestProcessing.GlRequestProcessor;
import team.hdt.blockadia.game_engine_old.client.resourceProcessing.ResourceRequest;

public class TextureLoadRequest implements ResourceRequest, GlRequest {

    private Texture texture;
    private TextureBuilder builder;
    private TextureData data;

    protected TextureLoadRequest(Texture texture, TextureBuilder builder) {
        this.texture = texture;
        this.builder = builder;
    }

    @Override
    public void doResourceRequest() {
        this.data = TextureManager.decodeTextureFile(builder.getFile());
        GlRequestProcessor.sendRequest(this);
    }

    @Override
    public void executeGlRequest() {
        int texID = TextureManager.loadTextureToOpenGL(data, builder);
        texture.setTextureID(texID);
    }

}
