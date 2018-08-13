package team.hdt.sandboxgame.game_engine.client.rendering.textures;

import team.hdt.sandboxgame.game_engine.client.glRequestProcessing.GlRequest;
import team.hdt.sandboxgame.game_engine.client.glRequestProcessing.GlRequestProcessor;
import team.hdt.sandboxgame.game_engine.client.resourceProcessing.ResourceRequest;

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
