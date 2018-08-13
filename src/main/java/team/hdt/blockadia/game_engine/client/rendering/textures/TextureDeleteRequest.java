package team.hdt.blockadia.game_engine.client.rendering.textures;

import team.hdt.blockadia.game_engine.client.glRequestProcessing.GlRequest;

public class TextureDeleteRequest implements GlRequest {
	
	private int textureID;
	
	public TextureDeleteRequest(int textureID){
		this.textureID = textureID;
	}

	@Override
	public void executeGlRequest() {
		TextureManager.deleteTexture(textureID);
	}

}
