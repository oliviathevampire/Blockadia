package team.hdt.blockadia.game_engine.client.rendering.fontRendering;

import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;
import team.hdt.blockadia.game_engine.util.toolbox.OpenglUtils;

import java.util.List;
import java.util.Map;

/**
 * Renderer used for rendering text to the screen.
 * @author Karl
 *
 */
public class FontRenderer {

	private FontShader shader;

	/**
	 * Creates a new font renderer and initialises the shader program.
	 */
	public FontRenderer() {
		shader = new FontShader();
	}

	/**
	 * Renders a load of texts to the screen.
	 * @param texts
	 */
	public void render(Map<FontType, List<Text>> texts) {
		prepare();
		for (FontType font : texts.keySet()) {
			OpenglUtils.bindTextureToBank(font.getTextureAtlas(), 0);
			for(Text text : texts.get(font)){
				renderText(text);
			}
		}
		endRendering();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}

	private void prepare(){
		OpenglUtils.antialias(false);
		OpenglUtils.enableAlphaBlending();
		OpenglUtils.disableDepthTesting();
		OpenglUtils.cullBackFaces(true);
		shader.start();
	}
	
	private void endRendering(){
		shader.stop();
		OpenglUtils.disableBlending();
		OpenglUtils.enableDepthTesting();
	}
	
	private void renderText(Text text) {
		if(text.isEmpty()){
			return;
		}
		OpenglUtils.bindVAO(text.getMesh(), 0, 1);
		Vectors2f position = text.getPosition();
		setScissorTest(text.getClippingBounds());
		shader.transform.loadVec3(position.x, position.y, text.getScale());
		Colour colour = text.getColour();
		shader.colour.loadVec4(colour.getR(), colour.getG(), colour.getB(), text.getTransparency());
		shader.borderColour.loadVec3(text.getBorderColour().getVector());
		shader.edgeData.loadVec2(text.calculateEdgeStart(), text.calculateAntialiasSize());
		shader.borderSizes.loadVec2(text.getTotalBorderSize(), text.getGlowSize());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		OpenglUtils.unbindVAO(0, 1);
	}
	
	
	private void setScissorTest(int[] bounds){
		if(bounds==null){
			OpenglUtils.disableScissorTest();
		}else{
			OpenglUtils.enableScissorTest(bounds[0], bounds[1], bounds[2], bounds[3]);
		}
	}

}
